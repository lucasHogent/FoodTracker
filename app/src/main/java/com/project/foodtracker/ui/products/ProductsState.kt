package com.project.foodtracker.ui.products
import com.project.foodtracker.domain.model.ProductModel

data class ProductsState (
    val isLoading: Boolean = false,
    val products: List<ProductModel> = emptyList(),
    val error: String = "",
)
