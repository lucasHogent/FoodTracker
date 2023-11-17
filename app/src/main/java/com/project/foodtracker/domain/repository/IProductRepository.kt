package com.project.foodtracker.domain.repository

import com.project.foodtracker.domain.model.ProductModel
import dagger.Provides
import kotlinx.coroutines.flow.Flow


interface IProductRepository {

    val products: Flow<List<ProductModel>>
    fun getProductById(productId: String): Flow<ProductModel>
    fun searchAllProductsByName(name: String): Flow<List<ProductModel>>
    suspend fun refreshDatabase()

}