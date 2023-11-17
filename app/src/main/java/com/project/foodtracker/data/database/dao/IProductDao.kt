package com.project.foodtracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.foodtracker.data.database.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IProductDao {
    @Insert
    fun insert(product: ProductEntity)

    @Query("SELECT * from products WHERE productId = :key")
    fun get(key: String): Flow<ProductEntity>

    @Query("SELECT * FROM products ORDER BY productId DESC")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("DELETE FROM products")
    fun clear()

    @Query("SELECT * FROM products ORDER BY title DESC")
    fun getAllProductsLive(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<ProductEntity>)

}
