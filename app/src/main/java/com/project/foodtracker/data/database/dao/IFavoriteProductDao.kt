package com.project.foodtracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.project.foodtracker.data.database.entities.FavoriteEntity
import com.project.foodtracker.data.database.entities.FavoriteWithProduct

/**
 * Data Access Object (DAO) interface for handling operations related to favorite products in the local database.
 */
@Dao
interface IFavoriteProductDao {

    /**
     * Inserts a favorite product into the database or replaces it if it already exists.
     *
     * @param product The favorite product entity to be inserted or replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: FavoriteEntity)

    /**
     * Retrieves a list of all favorite products along with their details.
     *
     * @return A list of [FavoriteWithProduct] entities representing favorite products.
     */
    @Transaction
    @Query("SELECT * FROM favorites INNER JOIN products ON favorites.productId = products.productId")
    suspend fun getFavoriteProducts(): List<FavoriteWithProduct>

    /**
     * Retrieves the details of a favorite product by its unique identifier.
     *
     * @param productId The unique identifier of the favorite product.
     * @return The [FavoriteWithProduct] entity representing the specified favorite product.
     */
    @Transaction
    @Query("SELECT * FROM favorites " +
            "JOIN products ON favorites.productId = products.productId " +
            "WHERE favorites.productId = :productId ")
    suspend fun getFavoriteByProductId(productId: String): FavoriteWithProduct

    /**
     * Deletes a favorite product from the database based on its unique identifier.
     *
     * @param productId The unique identifier of the product to be deleted from favorites.
     */
    @Query("DELETE FROM favorites WHERE productId = :productId")
    suspend fun deleteFavoriteByProductId(productId: String)

    /**
     * Clears all favorite products from the database.
     */
    @Query("DELETE FROM favorites")
    fun clear()
}

