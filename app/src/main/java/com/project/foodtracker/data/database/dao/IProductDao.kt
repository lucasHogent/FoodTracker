package com.project.foodtracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.project.foodtracker.data.database.entities.ProductEntity
import com.project.foodtracker.data.database.entities.ProductWithIngredientsCrossRef

@Dao
interface IProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: ProductEntity)
    @Insert
    fun insert(productWithIngredientsCrossRef: ProductWithIngredientsCrossRef)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg products: ProductEntity)

    @Transaction
    @Query("SELECT * from products WHERE productId = :key")
    fun get(key: String): ProductEntity

    @Transaction
    @Query("SELECT * FROM products ORDER BY productId DESC")
    fun getAllProducts(): List<ProductEntity>

    @Query("DELETE FROM products")
    fun clear()

    @Transaction
    @Query("SELECT * FROM products WHERE title LIKE '%' || :title || '%'")
    fun getAllProductsByTitle(title: String): List<ProductEntity>
}
