package com.project.foodtracker.ui.product_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.foodtracker.common.Constants
import com.project.foodtracker.common.Resource
import com.project.foodtracker.domain.model.ProductDetailModel
import com.project.foodtracker.domain.use_case.GetProductDetailUseCase
import com.project.foodtracker.domain.use_case.UpsertProductUseCase
import com.project.foodtracker.ui.product.ProductDetailState
import com.project.foodtracker.ui.util.Screen
import com.project.foodtracker.ui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductDetailEditViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val upsertProductUseCase: UpsertProductUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _productState = MutableStateFlow(ProductDetailState())
    val productState = _productState.asStateFlow()

    var initialProduct: ProductDetailModel? = null

    init {
        savedStateHandle.get<String>(Constants.PARAM_PRODUCT_ID)?.let { productId ->
            getProduct(productId)
        }
    }

    fun updateProduct(product: ProductDetailModel){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                upsertProductUseCase(product)
            }
        }
    }

    private fun getProduct(productId: String) {
        getProductDetailUseCase(productId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _productState.value = ProductDetailState(product = result.data)
                    initialProduct = ProductDetailState(product = result.data).product

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
                initialProduct?.let { product ->
                    viewModelScope.launch {
                        upsertProductUseCase(initialProduct!!)
                    }
                }
                _productState.update { it.copy(product = initialProduct) }
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