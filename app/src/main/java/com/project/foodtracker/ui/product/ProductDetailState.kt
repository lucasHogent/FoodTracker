package com.project.foodtracker.ui.product

import com.project.foodtracker.domain.model.ProductDetailModel

data class ProductDetailState(
    val isLoading: Boolean = false,
    val product: ProductDetailModel? = null,
    val error: String = ""
)