package com.project.foodtracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.project.foodtracker.data.database.entities.ProductEntity
import com.project.foodtracker.data.database.entities.ProductWithIngredientsCrossRef

/**
 * Data Access Object (DAO) interface for handling operations related to products in the local database.
 */
@Dao
interface IProductDao {

    /**
     * Inserts a product into the database or replaces it if it already exists.
     *
     * @param product The product entity to be inserted or replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: ProductEntity)

    /**
     * Inserts a cross-reference between a product and its ingredients into the database.
     *
     * @param productWithIngredientsCrossRef The cross-reference entity to be inserted.
     */
    @Insert
    fun insert(productWithIngredientsCrossRef: ProductWithIngredientsCrossRef)

    /**
     * Inserts multiple products into the database or replaces them if they already exist.
     *
     * @param products The vararg list of product entities to be inserted or replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg products: ProductEntity)

    /**
     * Retrieves a product from the database based on its unique identifier.
     *
     * @param key The unique identifier of the product.
     * @return The [ProductEntity] representing the specified product.
     */
    @Transaction
    @Query("SELECT * from products WHERE productId = :key")
    fun get(key: String): ProductEntity

    /**
     * Retrieves a list of all products in the database, ordered by product ID in descending order.
     *
     * @return A list of [ProductEntity] entities representing all products.
     */
    @Transaction
    @Query("SELECT * FROM products ORDER BY title ASC")
    fun getAllProducts(): List<ProductEntity>

    /**
     * Clears all products from the database.
     */
    @Query("DELETE FROM products")
    fun clear()

    /**
     * Retrieves a list of products from the database that match the specified title.
     *
     * @param title The title to be used for searching products.
     * @return A list of [ProductEntity] entities representing products with matching titles.
     */
    @Transaction
    @Query("SELECT * FROM products WHERE title LIKE '%' || :title || '%' ORDER BY title ASC")
    fun getAllProductsByTitle(title: String): List<ProductEntity>

    /**
     * Deletes product from the database.
     */
    @Query("DELETE FROM products WHERE productId = :id")
    fun delete(id: String)

}

