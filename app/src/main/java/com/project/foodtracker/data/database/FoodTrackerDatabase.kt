package com.project.foodtracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.foodtracker.data.database.dao.IFavoriteProductDao
import com.project.foodtracker.data.database.dao.IIngredientDao
import com.project.foodtracker.data.database.dao.IProductDao
import com.project.foodtracker.data.database.entities.FavoriteEntity
import com.project.foodtracker.data.database.entities.FavoriteWithProduct
import com.project.foodtracker.data.database.entities.IngredientEntity
import com.project.foodtracker.data.database.entities.ProductEntity
import com.project.foodtracker.data.database.entities.ProductWithIngredientsCrossRef

@Database(entities = [
    ProductEntity::class,
    ProductWithIngredientsCrossRef::class,
    FavoriteEntity::class,
    IngredientEntity::class],
    exportSchema = true,
    version = 2)
@TypeConverters(StringListConverter::class)
abstract class FoodTrackerDatabase : RoomDatabase() {
    abstract fun productDao(): IProductDao
    abstract fun favoriteProductDao(): IFavoriteProductDao
    abstract fun ingredientDao(): IIngredientDao

}