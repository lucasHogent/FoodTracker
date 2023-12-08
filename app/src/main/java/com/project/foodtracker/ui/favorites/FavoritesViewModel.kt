package com.project.foodtracker.ui.favorites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.foodtracker.common.Resource
import com.project.foodtracker.domain.use_case.GetFavoritesUseCase
import com.project.foodtracker.ui.product_list.ProductListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val _productListState = mutableStateOf(ProductListState())
    val productListState: State<ProductListState> = _productListState

    init {
        refresh()
    }

   fun refresh(){
        viewModelScope.launch {
            getFavoritesUseCase().onEach { result ->
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