package com.project.foodtracker.ui.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.foodtracker.common.Constants
import com.project.foodtracker.common.Resource
import com.project.foodtracker.domain.use_case.AddToFavoritesUseCase
import com.project.foodtracker.domain.use_case.GetFavoriteProductUseCase
import com.project.foodtracker.domain.use_case.GetProductDetailUseCase
import com.project.foodtracker.domain.use_case.RemoveFromFavoritesUseCase
import com.project.foodtracker.domain.use_case.UpsertProductUseCase
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

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _productState = MutableStateFlow(ProductDetailState())
    val productState: StateFlow<ProductDetailState> = _productState

    private val _isFavoriteProduct = MutableStateFlow(false)
    val isFavoriteProduct: StateFlow<Boolean> = _isFavoriteProduct

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

    fun onEvent(event: ProductDetailEvent) {
        when (event) {
            is ProductDetailEvent.OnAddFavoriteProductClick -> {
                addToFavorites(event.product.productId)
                sendUiEvent(
                    UiEvent.ShowSnackbar(
                        message = "Added ${event.product.title} to favorites",
                        action = "Undo"
                    )
                )
            }
            is ProductDetailEvent.OnRemoveFavoriteProductClick -> {
                removeFromFavorites(event.product.productId)
                sendUiEvent(
                    UiEvent.ShowSnackbar(
                        message = "Removed ${event.product.title} from favorites",
                        action = "Undo"
                    )
                )
            }
            is ProductDetailEvent.OnClickEditProductDetail -> {
                sendUiEvent(UiEvent.Navigate(Screen.ProductDetailEdit.route + "?productId=${event.productId}"))
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