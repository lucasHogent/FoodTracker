package com.project.foodtracker.ui.discover

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.foodtracker.common.Resource
import com.project.foodtracker.domain.use_case.GetProductsUseCase
import com.project.foodtracker.ui.product_list.ProductListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _productListState = mutableStateOf(ProductListState())
    val productListState: State<ProductListState> = _productListState

    private val _searchString = MutableStateFlow("")
    val searchString: StateFlow<String> = _searchString

    init {
        getProducts("pasta")
    }

    fun getProducts(query : String) {
        getProductsUseCase(query).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _productListState.value = ProductListState(products = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _productListState.value = ProductListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _productListState.value = ProductListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
    fun searchNameChanged(name: String) {
        _searchString.value = name
        searchProduct(name)
    }

    fun clearInput() {
        _searchString.value = ""
        _productListState.value = ProductListState()
    }

    init {
        viewModelScope.launch {
        }
    }

    fun searchProduct(query: String) {

        if (query.isNotBlank()) {
            getProductsUseCase("").onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _productListState.value = ProductListState(products = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _productListState.value = ProductListState(
                            error = result.message ?: "An unexpected error occured"
                        )
                    }
                    is Resource.Loading -> {
                        _productListState.value = ProductListState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}
