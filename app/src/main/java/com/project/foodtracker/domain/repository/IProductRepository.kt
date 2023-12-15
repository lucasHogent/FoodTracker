package com.project.foodtracker.domain.repository

import com.project.foodtracker.domain.model.ProductDetailModel
import com.project.foodtracker.domain.model.ProductModel

/**
 * Interface for managing product-related data operations.
 */
interface IProductRepository {
    /**
     * Retrieves a product by its unique identifier.
     *
     * @param productId The unique identifier of the product.
     * @return The details of the specified product.
     */
    suspend fun getProductById(productId: String): ProductDetailModel

    /**
     * Searches for products based on their titles.
     *
     * @param title The title to search for.
     * @return A list of products matching the search criteria.
     */
    suspend fun searchAllProductsByTitle(title: String): List<ProductModel>

    /**
     * Clears all products from the repository.
     */
    suspend fun clear()

    /**
     * Retrieves all products stored in the repository.
     *
     * @return A list of all products.
     */
    suspend fun getAllProducts(): List<ProductModel>

    /**
     * Retrieves the count of products stored in the repository.
     *
     * @return The total count of products.
     */
    suspend fun getCountDBProducts(): Int

    /**
     * Updates the details of a product in the repository.
     *
     * @param product The updated details of the product.
     */
    suspend fun updateProduct(product: ProductDetailModel)

    /**
     * deletes the details of a product in the repository.
     *
     * @param productId The id of the product.
     */
    suspend fun deleteProduct(productId: String)
}