package com.project.foodtracker.ui.product_list

import com.project.foodtracker.domain.model.ProductModel

sealed class ProductsListEvent {
    class OnDeleteProductClick(val product: ProductModel) : ProductsListEvent()
    object OnUndoDeleteClick : ProductsListEvent()
    data class OnProductClick(val product: ProductModel) : ProductsListEvent()
    data class OnSearchNameChanged(val name: String) : ProductsListEvent()
    object OnSearchClick : ProductsListEvent()
    object OnClearSearchClick : ProductsListEvent()
}
