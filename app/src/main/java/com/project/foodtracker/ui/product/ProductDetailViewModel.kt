package com.project.foodtracker.ui.product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.foodtracker.common.Constants
import com.project.foodtracker.common.Resource
import com.project.foodtracker.domain.use_case.GetProductDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(ProductDetailState())
    val state: State<ProductDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_PRODUCT_ID)?.let { productId ->
            getProduct(productId)
        }
    }

    private fun getProduct(productId: String) {
        getProductDetailUseCase(productId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ProductDetailState(product = result.data)
                }
                is Resource.Error -> {
                    _state.value = ProductDetailState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = ProductDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}