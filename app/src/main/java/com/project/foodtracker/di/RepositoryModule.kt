package com.project.foodtracker.di

import com.project.foodtracker.data.repository.ProductRepository
import com.project.foodtracker.domain.repository.IProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindRepository(impl: ProductRepository): IProductRepository
}