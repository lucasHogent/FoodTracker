package com.project.foodtracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
 import com.project.foodtracker.data.database.dao.IIngredientDao
import com.project.foodtracker.data.database.dao.IProductDao
import com.project.foodtracker.data.database.entities.FavoriteEntity
import com.project.foodtracker.data.database.entities.IngredientEntity
import com.project.foodtracker.data.database.entities.ProductEntity
import com.project.foodtracker.data.database.entities.ProductWithIngredientsCrossRef

/**
 * Room database class for testing [IProductDao] and [IIngredientDao].
 *
 * This database is specifically designed for testing and extends [RoomDatabase].
 * It defines the entities [ProductEntity], [ProductWithIngredientsCrossRef], and [IngredientEntity],
 * and provides access to the corresponding DAOs.
 *
 * @property productDao Access point for the [IProductDao] DAO.
 * @property ingredientDao Access point for the [IIngredientDao] DAO.
 *
 * @see IProductDao
 * @see IIngredientDao
 * @see ProductEntity
 * @see ProductWithIngredientsCrossRef
 * @see IngredientEntity
 * @see StringListConverter
 */

@Database(entities = [
    ProductEntity::class,
    ProductWithIngredientsCrossRef::class,
    FavoriteEntity::class,
    IngredientEntity::class],
    version = 1, exportSchema = true)
@TypeConverters(StringListConverter::class)
abstract class FoodTrackerTestDatabase : RoomDatabase() {
    /**
     * Access point for the [IProductDao] DAO.
     *
     * @return The [IProductDao] DAO.
     */
    abstract fun productDao(): IProductDao

    /**
     * Access point for the [IIngredientDao] DAO.
     *
     * @return The [IIngredientDao] DAO.
     */
    abstract fun ingredientDao(): IIngredientDao

}