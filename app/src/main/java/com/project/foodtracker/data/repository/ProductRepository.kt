package com.project.foodtracker.data.repository

 import com.project.foodtracker.data.database.dao.IProductDao
 import com.project.foodtracker.data.database.entities.asDomain
 import com.project.foodtracker.data.remote.IProductApiService
import com.project.foodtracker.domain.model.product.ProductDetailModel
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

    override val products: List<ProductModel>

        get() = getAllProducts()


    fun getAllProducts() : List<ProductModel> {
        return emptyList()
    }

    override suspend fun refreshDatabase() {
        productDao.clear()
    }


    override fun getProductById(productId: String): ProductDetailModel {
        TODO("Not yet implemented")
    }

    override fun searchAllProductsByName(name: String): List<ProductDetailModel> {
        TODO("Not yet implemented")
    }

}