package com.project.foodtracker.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.project.foodtracker.data.database.entities.Product

@Dao
interface IProductDao {
    @Insert
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Query("SELECT * from product_table WHERE id = :key")
    suspend fun get(key: String): Product?

    @Query("SELECT * FROM product_table ORDER BY id DESC")
    suspend fun getAllProducts(): List<Product>

    @Query("DELETE FROM product_table")
    suspend fun clear()

    @Query("SELECT * FROM product_table ORDER BY name DESC")
    fun getAllProductsLive(): LiveData<List<Product>>

}
