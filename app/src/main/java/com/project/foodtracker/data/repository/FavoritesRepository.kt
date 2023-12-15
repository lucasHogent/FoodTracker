package com.project.foodtracker.data.repository

import com.project.foodtracker.data.database.dao.IFavoriteProductDao
import com.project.foodtracker.data.database.entities.FavoriteEntity
import com.project.foodtracker.data.database.entities.asProductDetailModel
import com.project.foodtracker.data.database.entities.asProductModel
import com.project.foodtracker.domain.model.ProductDetailModel
import com.project.foodtracker.domain.model.ProductModel
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

    override suspend fun getAllFavoriteProducts(): List<ProductModel> {
        return favoritesDao.getFavoriteProducts().map { p -> p.product.asProductModel() }
    }

    override suspend fun getFavoriteProduct(productId: String): ProductDetailModel {

        var favoriteWithProduct = favoritesDao.getFavoriteByProductId(productId)
        Timber.i("getFavoriteProduct: Getting %s", favoriteWithProduct?.toString())
        return favoriteWithProduct?.product?.asProductDetailModel()
            ?: throw NoSuchElementException("No favorite product found with productId: $productId")
    }

    override suspend fun deleteFavorite(productId: String) {
        Timber.i("deleteFavorite: Deleting %s", productId)
        var favoriteWithProduct = favoritesDao.deleteFavoriteByProductId(productId)
    }

    override suspend fun clear() {
        favoritesDao.clear()
    }
}