package com.project.foodtracker.domain.repository

import com.project.foodtracker.domain.model.ProductDetailModel
import com.project.foodtracker.domain.model.ProductModel

interface IFavoritesRepository {
    suspend fun addProductToFavorites(productId: String)
    suspend fun getAllFavoriteProducts(): List<ProductModel>
    suspend fun getFavoriteProduct(productId: String): ProductDetailModel
    suspend fun deleteFavorite(productId: String)
}
