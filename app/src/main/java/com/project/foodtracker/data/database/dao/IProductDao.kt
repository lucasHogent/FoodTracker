package com.project.foodtracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.foodtracker.data.database.entities.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface IProductDao {
    @Insert
    fun insert(product: Product)

    @Query("SELECT * from product_table WHERE id = :key")
    fun get(key: String): Flow<Product>

    @Query("SELECT * FROM product_table ORDER BY id DESC")
    fun getAllProducts(): Flow<List<Product>>

    @Query("DELETE FROM product_table")
    fun clear()

    @Query("SELECT * FROM product_table ORDER BY name DESC")
    fun getAllProductsLive(): Flow<List<Product>>

}
