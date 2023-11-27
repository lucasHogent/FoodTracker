package com.project.foodtracker.domain.repository

import com.project.foodtracker.domain.model.product.ProductDetailModel
import com.project.foodtracker.domain.model.product.ProductModel
import kotlinx.coroutines.flow.Flow

interface IProductRepository {

    val products: List<ProductModel>
    fun getProductById(productId: String): ProductDetailModel
    fun searchAllProductsByName(name: String): List<ProductDetailModel>
    suspend fun refreshDatabase()

}