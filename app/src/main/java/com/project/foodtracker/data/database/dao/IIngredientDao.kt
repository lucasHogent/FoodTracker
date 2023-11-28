package com.project.foodtracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.foodtracker.data.database.entities.IngredientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IIngredientDao {
    @Insert
    fun insert(nutrition: IngredientEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg ingredients: IngredientEntity)

    @Query("SELECT * from ingredients WHERE ingredientId = :key")
    fun get(key: String): Flow<IngredientEntity>

    @Query("SELECT * from ingredients WHERE name = :name")
    fun getByName(name: String): Flow<IngredientEntity>

    @Query("SELECT * FROM ingredients ORDER BY name DESC")
    fun getAllIngredients(): List<IngredientEntity>

    @Query("DELETE FROM ingredients")
    fun clear()
}