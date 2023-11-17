package com.project.foodtracker.data.repository

import com.project.foodtracker.common.Resource
import com.project.foodtracker.data.database.dao.IProductDao
import com.project.foodtracker.data.database.entities.asDomainModel
import com.project.foodtracker.data.remote.IProductApiService
import com.project.foodtracker.data.remote.dto.product.toProductEntity
import com.project.foodtracker.domain.model.product.ProductModel
import com.project.foodtracker.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productDao: IProductDao,
    private val productApi: IProductApiService
) : IProductRepository {

    override val products: Flow<List<ProductModel>>

        get() = productDao.getAllProducts().map {
            it.asDomainModel()
        }

    override suspend fun refreshDatabase() {
        val getProductsDeferred = productApi.getProducts(10)
        val products = getProductsDeferred.await().map{it.toProductEntity()}
        productDao.insertAll(products)
    }


    override fun getProductById(productId: String): Flow<ProductModel> {
        try {
            val productFlow = productDao.get(productId)
            return productFlow.map {
                it.asDomainModel()
            }
        } catch (e: Exception) {
            //Timber.e(e)
        }
        return flow {}
    }

    override fun searchAllProductsByName(name: String): Flow<List<ProductModel>> {
        TODO("Not yet implemented")
    }

}