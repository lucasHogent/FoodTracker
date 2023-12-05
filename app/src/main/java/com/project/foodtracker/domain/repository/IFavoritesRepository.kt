package com.project.foodtracker.domain.repository

import com.project.foodtracker.data.database.entities.FavoriteWithProduct

interface IFavoritesRepository {
    suspend fun addProductToFavorites(productId: String)

    suspend fun getAllFavoriteWithProduct(): List<FavoriteWithProduct>
    suspend fun getFavoriteProduct(productId: String): FavoriteWithProduct
    suspend fun deleteFavorite(productId: String)
}
