package com.project.foodtracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.foodtracker.data.database.dao.IFavoriteProductDao
import com.project.foodtracker.data.database.dao.IProductDao
import com.project.foodtracker.data.database.entities.FavoriteProduct
import com.project.foodtracker.data.database.entities.Product

@Database(entities = [Product::class, FavoriteProduct::class], version = 1)
abstract class FoodTrackerTestDatabase : RoomDatabase() {
    abstract fun productDao(): IProductDao
    abstract fun favoriteProductDao(): IFavoriteProductDao

}