package com.project.foodtracker.ui.product_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.foodtracker.common.Resource
import com.project.foodtracker.domain.use_case.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ProductListState())
    val state: State<ProductListState> = _state

    private val _searchString = MutableStateFlow("")
    val searchString: StateFlow<String> = _searchString

    init {
        getProducts("")
    }


    fun searchNameChanged(query: String) {
        _searchString.value = query
        getProducts(query)
    }
    fun clearInput() {
        _searchString.value = ""
        _state.value = ProductListState()
    }
    public fun getProducts(query : String) {
        getProductsUseCase(query).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ProductListState(products = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = ProductListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = ProductListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}