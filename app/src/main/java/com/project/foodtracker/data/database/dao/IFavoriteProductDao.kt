package com.project.foodtracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.project.foodtracker.data.database.entities.FavoriteEntity
import com.project.foodtracker.data.database.entities.FavoriteWithProduct

@Dao
interface IFavoriteProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: FavoriteEntity)

    @Transaction
    @Query("SELECT * FROM favorites INNER JOIN products ON favorites.productId = products.productId")
    suspend fun getFavoriteProducts(): List<FavoriteWithProduct>

    @Transaction
    @Query("SELECT * FROM favorites " +
            "JOIN products ON favorites.productId = products.productId " +
            "WHERE favorites.productId = :productId ")
    suspend fun getFavoriteByProductId(productId: String): FavoriteWithProduct


    @Query("DELETE FROM favorites WHERE productId = :productId")
    suspend fun deleteFavoriteByProductId(productId: String)
}
