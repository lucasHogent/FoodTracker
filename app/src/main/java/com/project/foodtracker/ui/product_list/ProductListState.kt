package com.project.foodtracker.ui.product_list

import com.project.foodtracker.domain.model.ProductModel

data class ProductListState(
    val isLoading: Boolean = false,
    val products: List<ProductModel> = emptyList(),
    val error: String = ""
)