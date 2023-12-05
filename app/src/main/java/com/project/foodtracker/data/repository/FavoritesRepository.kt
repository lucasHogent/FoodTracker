package com.project.foodtracker.data.repository

import com.project.foodtracker.data.database.dao.IFavoriteProductDao
import com.project.foodtracker.data.database.dao.IProductDao
import com.project.foodtracker.data.database.entities.FavoriteEntity
import com.project.foodtracker.data.database.entities.FavoriteWithProduct
import com.project.foodtracker.domain.model.ProductDetailModel
import com.project.foodtracker.domain.repository.IFavoritesRepository
import timber.log.Timber
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val favoritesDao: IFavoriteProductDao,) : IFavoritesRepository {
    override suspend fun addProductToFavorites(productId: String)  {

        var favorite = FavoriteEntity(0, productId)
        Timber.i("addProductToFavorites: Adding %s", favorite.productId)
        return favoritesDao.insert(favorite)
    }

    override suspend fun getAllFavoriteWithProduct(): List<FavoriteWithProduct> {
        return favoritesDao.getFavoriteProducts()
    }

    override suspend fun getFavoriteProduct(productId: String): FavoriteWithProduct {

        var favoriteWithProduct = favoritesDao.getFavoriteByProductId(productId)
        Timber.i("getFavoriteProduct: Getting %s", favoriteWithProduct?.toString())
        return favoriteWithProduct
    }

    override suspend fun deleteFavorite(productId: String) {
        Timber.i("deleteFavorite: Deleting %s", productId)
        var favoriteWithProduct = favoritesDao.deleteFavoriteByProductId(productId)
    }
}