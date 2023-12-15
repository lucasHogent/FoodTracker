package com.project.foodtracker.domain.repository

import com.project.foodtracker.domain.model.ProductDetailModel
import com.project.foodtracker.domain.model.ProductModel

/**
 * Interface for managing favorite product-related data operations.
 */
interface IFavoritesRepository {
    /**
     * Adds a product to the list of favorites.
     *
     * @param productId The unique identifier of the product to be added to favorites.
     */
    suspend fun addProductToFavorites(productId: String)

    /**
     * Retrieves a list of all favorite products.
     *
     * @return A list of all products marked as favorites.
     */
    suspend fun getAllFavoriteProducts(): List<ProductModel>

    /**
     * Retrieves the details of a favorite product by its unique identifier.
     *
     * @param productId The unique identifier of the favorite product.
     * @return The details of the specified favorite product.
     */
    suspend fun getFavoriteProduct(productId: String): ProductDetailModel

    /**
     * Deletes a product from the list of favorites.
     *
     * @param productId The unique identifier of the product to be removed from favorites.
     */
    suspend fun deleteFavorite(productId: String)

    /**
     * Clears all favorite products from the repository.
     */
    suspend fun clear()
}