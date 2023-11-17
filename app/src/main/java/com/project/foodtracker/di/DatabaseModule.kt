package com.project.foodtracker.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import com.project.foodtracker.data.database.FoodTrackerDatabase
import com.project.foodtracker.data.database.dao.IProductDao
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): FoodTrackerDatabase {
        return Room.databaseBuilder(
            appContext,
            FoodTrackerDatabase::class.java,"foodtracker.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideProductDao(database: FoodTrackerDatabase): IProductDao {
        return database.productDao()
    }
}