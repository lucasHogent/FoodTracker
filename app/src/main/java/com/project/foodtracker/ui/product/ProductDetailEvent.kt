package com.project.foodtracker.ui.product

import com.project.foodtracker.domain.model.ProductDetailModel


sealed class ProductDetailEvent {
    class OnAddFavoriteProductClick(val product: ProductDetailModel): ProductDetailEvent()
    class OnRemoveFavoriteProductClick(val product: ProductDetailModel): ProductDetailEvent()
    class OnClickEditProductDetail(val productId: String): ProductDetailEvent()
}