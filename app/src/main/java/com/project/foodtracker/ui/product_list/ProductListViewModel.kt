package com.project.foodtracker.ui.product_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.foodtracker.common.Resource
import com.project.foodtracker.domain.model.ProductDetailModel
import com.project.foodtracker.domain.model.asProductModel
import com.project.foodtracker.domain.use_case.DeleteProductUseCase
import com.project.foodtracker.domain.use_case.GetFavoritesUseCase
import com.project.foodtracker.domain.use_case.GetProductDetailUseCase
import com.project.foodtracker.domain.use_case.GetProductsUseCase
import com.project.foodtracker.domain.use_case.UpsertProductUseCase
import com.project.foodtracker.ui.util.Screen
import com.project.foodtracker.ui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val upsertProductUseCase: UpsertProductUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(ProductListState())
    val state: State<ProductListState> = _state

    private val _searchString = MutableStateFlow("")
    val searchString: StateFlow<String> = _searchString

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedProduct: ProductDetailModel? = null

    fun getFavoriteProducts() {
        viewModelScope.launch {
            getFavoritesUseCase().onEach { result ->
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

    fun getProducts(query: String) {
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

    fun onEvent(event: ProductsListEvent) {
        when (event) {
            is ProductsListEvent.OnProductClick -> {
                sendUiEvent(UiEvent.Navigate(Screen.ProductDetail.route + "?productId=${event.product.id}"))
            }
            is ProductsListEvent.OnDeleteProductClick -> {

                viewModelScope.launch {
                    getProductDetailUseCase(event.product.id).collect { result ->
                        deletedProduct = result.data
                    }
                    deleteProductUseCase(event.product.id)
                    _state.value = _state.value.copy(
                        products = _state.value.products.filterNot {
                            it.id == (deletedProduct?.productId ?: "")
                        }
                    )
                    sendUiEvent(
                        UiEvent.ShowSnackbar(
                            message = "Product deleted",
                            action = "Undo"
                        )
                    )
                }
            }
            is ProductsListEvent.OnUndoDeleteClick -> {
                deletedProduct?.let { product ->
                    viewModelScope.launch {
                        upsertProductUseCase(deletedProduct!!)
                    }
                }
                _state.value = _state.value.copy(
                    products = (_state.value.products + (deletedProduct?.asProductModel() ?: return))
                        .sortedBy{ it.title }
                )

            }
            is ProductsListEvent.OnSearchNameChanged -> {

                _searchString.value = event.name
                Timber.i("typed: %s", event.name)
                Timber.i(searchString.value)
                getProducts(event.name)
            }
            is ProductsListEvent.OnClearSearchClick -> {
                Timber.i("cleared search")
                _searchString.value = ""
                _state.value = ProductListState()
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