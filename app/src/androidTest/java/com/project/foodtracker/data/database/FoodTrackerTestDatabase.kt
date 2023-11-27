package com.project.foodtracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
 import com.project.foodtracker.data.database.dao.IIngredientDao
import com.project.foodtracker.data.database.dao.IProductDao
import com.project.foodtracker.data.database.entities.IngredientEntity
import com.project.foodtracker.data.database.entities.ProductEntity
import com.project.foodtracker.data.database.entities.ProductWithIngredientsCrossRef

@Database(entities = [
    ProductEntity::class,
    ProductWithIngredientsCrossRef::class,
    IngredientEntity::class],
    version = 1, exportSchema = true)
@TypeConverters(StringListConverter::class)
abstract class FoodTrackerTestDatabase : RoomDatabase() {
    abstract fun productDao(): IProductDao
    abstract fun ingredientDao(): IIngredientDao

}