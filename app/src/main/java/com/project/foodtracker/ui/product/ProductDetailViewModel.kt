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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

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
}