package com.project.foodtracker.ui.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.foodtracker.common.Constants
import com.project.foodtracker.common.Resource
import com.project.foodtracker.domain.model.ProductDetailModel
import com.project.foodtracker.domain.use_case.AddToFavoritesUseCase
import com.project.foodtracker.domain.use_case.GetFavoriteProductUseCase
import com.project.foodtracker.domain.use_case.GetProductDetailUseCase
import com.project.foodtracker.domain.use_case.RemoveFromFavoritesUseCase
import com.project.foodtracker.domain.use_case.UpsertProductUseCase
import com.project.foodtracker.ui.product_edit.ProductDetailEditEvent
import com.project.foodtracker.ui.util.Screen
import com.project.foodtracker.ui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val getFavoriteProductUseCase: GetFavoriteProductUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val upsertProductUseCase: UpsertProductUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _productState = MutableStateFlow(ProductDetailState())
    val productState: StateFlow<ProductDetailState> = _productState

    private val _isFavoriteProduct = MutableStateFlow(false)
    val isFavoriteProduct: StateFlow<Boolean> = _isFavoriteProduct

    private var _initialProduct: ProductDetailModel? = null

    init {
        savedStateHandle.get<String>(Constants.PARAM_PRODUCT_ID)?.let { productId ->
            getProduct(productId)
            isFavoriteProduct(productId)
        }
    }

    fun addToFavorites(productId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                addToFavoritesUseCase(productId)
                isFavoriteProduct(productId)
            }
        }
    }

    fun removeFromFavorites(productId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                removeFromFavoritesUseCase(productId)
                isFavoriteProduct(productId)

            }
        }
    }

    private fun isFavoriteProduct(productId: String) {

        getFavoriteProductUseCase(productId)
            .onEach { result ->
                if (result is Resource.Success) {
                    _isFavoriteProduct.value = result.data != null
                }
            }
            .catch { exception ->
                _isFavoriteProduct.value = false
                Timber.i(exception.message)
            }
            .launchIn(viewModelScope)

    }

    private fun getProduct(productId: String) {
        getProductDetailUseCase(productId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _productState.value = ProductDetailState(product = result.data)
                    _initialProduct = ProductDetailState(product = result.data).product
                }

                is Resource.Error -> {
                    _productState.value = ProductDetailState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _productState.value = ProductDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateProduct(product: ProductDetailModel){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                upsertProductUseCase(product)
            }
        }
    }

    fun onEvent(event: ProductDetailEvent) {
        when (event) {
            is ProductDetailEvent.OnAddFavoriteProductClick -> {
                addToFavorites(event.product.productId)
                sendUiEvent(
                    UiEvent.ShowSnackbar(
                        message = "Added ${event.product.title} to favorites",

                    )
                )
            }

            is ProductDetailEvent.OnRemoveFavoriteProductClick -> {
                removeFromFavorites(event.product.productId)
                sendUiEvent(
                    UiEvent.ShowSnackbar(
                        message = "Removed ${event.product.title} from favorites",

                    )
                )
            }

            is ProductDetailEvent.OnClickEditProductDetail -> {
                sendUiEvent(UiEvent.Navigate(Screen.ProductDetailEdit.route + "?productId=${event.productId}"))
            }

            else -> {
                Unit
            }
        }
    }

    fun onEvent(event: ProductDetailEditEvent) {
        when (event) {
            is ProductDetailEditEvent.OnClickSaveProductDetail -> {
                updateProduct(_productState.value.product!!)
                Timber.i("product update saved")
                sendUiEvent(
                    UiEvent.ShowSnackbar(
                        message = "Product ${_productState.value.product!!.title} saved",
                        action = "Undo"
                    )
                )
            }

            is ProductDetailEditEvent.OnClickGoBack ->{
                Timber.i("go back: %s", _productState.value.product!!.productId)
                sendUiEvent(UiEvent.Navigate(Screen.ProductDetail.route + "?productId=${_productState.value.product!!.productId}"))
            }
            is ProductDetailEditEvent.OnProductTitleChanged -> {
                // validate event.product
                Timber.i("product update title: %s", event.title)
                _productState.update { currentState ->
                    currentState.copy(
                        product = currentState.product!!.copy(title = event.title)
                    )
                }
            }
            is ProductDetailEditEvent.OnProductImageChanged -> {
                // validate event.product
                Timber.i("product update image: %s", event.image)
                _productState.update { currentState ->
                    currentState.copy(
                        product = currentState.product!!.copy(image = event.image)
                    )
                }
            }
            is ProductDetailEditEvent.OnProductServingsChanged -> {
                // validate event.product
                Timber.i("product update servings: %s", event.servings)
                _productState.update { currentState ->
                    currentState.copy(
                        product = currentState.product!!.copy(servings = event.servings)
                    )
                }
            }
            is ProductDetailEditEvent.OnProductReadyInMinutesChanged -> {
                // validate event.product
                Timber.i("product update readyInMinutes: %s", event.readyInMinutes)
                _productState.update { currentState ->
                    currentState.copy(
                        product = currentState.product!!.copy(readyInMinutes = event.readyInMinutes)
                    )
                }
            }
            is ProductDetailEditEvent.OnProductHealthScoreChanged -> {
                // validate event.product
                Timber.i("product update healthScore: %s", event.healthScore)
                _productState.update { currentState ->
                    currentState.copy(
                        product = currentState.product!!.copy(healthScore = event.healthScore)
                    )
                }
            }
            is ProductDetailEditEvent.OnProductSpoonacularScoreChanged -> {
                // validate event.product
                Timber.i("product update spoonacularScore: %s", event.spoonacularScore)
                _productState.update { currentState ->
                    currentState.copy(
                        product = currentState.product!!.copy(spoonacularScore = event.spoonacularScore)
                    )
                }
            }
            is ProductDetailEditEvent.OnProductPricePerServingChanged -> {
                // validate event.product
                Timber.i("product update pricePerServing: %s", event.pricePerServing)
                _productState.update { currentState ->
                    currentState.copy(
                        product = currentState.product!!.copy(pricePerServing = event.pricePerServing)
                    )
                }
            }
            is ProductDetailEditEvent.OnProductCheapChanged -> {
                // validate event.product
                Timber.i("product update cheap: %s", event.cheap)
                _productState.update { currentState ->
                    currentState.copy(
                        product = currentState.product!!.copy(cheap = event.cheap)
                    )
                }
            }
            is ProductDetailEditEvent.OnProductCreditsTextChanged -> {
                // validate event.product
                Timber.i("product update title: %s", event.creditsText)
                _productState.update { currentState ->
                    currentState.copy(
                        product = currentState.product!!.copy(creditsText = event.creditsText)
                    )
                }
            }
            is ProductDetailEditEvent.OnClickUndoProductDetail -> {
                _initialProduct?.let { product ->
                    viewModelScope.launch {
                        upsertProductUseCase(_initialProduct!!)
                    }
                }
                _productState.update { it.copy(product = _initialProduct) }
            }

            else -> {Unit}
        }
    }


    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}