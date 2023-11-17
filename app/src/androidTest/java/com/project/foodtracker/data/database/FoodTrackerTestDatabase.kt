package com.project.foodtracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.foodtracker.data.database.dao.IFavoriteProductDao
import com.project.foodtracker.data.database.dao.IProductDao
import com.project.foodtracker.data.database.entities.IngredientEntity
import com.project.foodtracker.data.database.entities.NutrientEntity
import com.project.foodtracker.data.database.entities.ProductEntity

@Database(entities = [ProductEntity::class, IngredientEntity::class, NutrientEntity::class ], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class FoodTrackerTestDatabase : RoomDatabase() {
    abstract fun productDao(): IProductDao
    abstract fun favoriteProductDao(): IFavoriteProductDao

}