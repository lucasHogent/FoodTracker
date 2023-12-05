package com.project.foodtracker.data.repository

 import android.net.ConnectivityManager
 import android.net.NetworkCapabilities
 import com.project.foodtracker.data.database.dao.IProductDao
 import com.project.foodtracker.data.database.entities.asProductDetailModel
 import com.project.foodtracker.data.database.entities.asProductModel
 import com.project.foodtracker.data.remote.IProductApiService
 import com.project.foodtracker.data.remote.dto.asEntity
 import com.project.foodtracker.data.remote.dto.asProductDetailModel
 import com.project.foodtracker.domain.model.ProductDetailModel
 import com.project.foodtracker.domain.model.ProductModel
 import com.project.foodtracker.domain.model.asEntity
 import com.project.foodtracker.domain.repository.IProductRepository
 import kotlinx.coroutines.Dispatchers
 import kotlinx.coroutines.withContext
 import retrofit2.HttpException
 import java.io.IOException
 import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productDao: IProductDao,
    private val productApi: IProductApiService,
    private val connectivityManager: ConnectivityManager
) : IProductRepository {

    override suspend fun getAllProducts() : List<ProductModel> {
        return withContext(Dispatchers.IO) {
            // Check for internet connectivity
            if (isInternetConnected()) {
                try {
                    // Perform API call
                    val productsFromApi = productApi.getProducts("", 10)

                    val filteredProductEntities = productsFromApi.results
                        .map { it.asEntity() }
                        .filter { product ->
                             productDao.get(product.productId).productId != null
                        }

                    productDao.insertAll(*filteredProductEntities.toTypedArray())
                } catch (e: IOException) {
                    // Handle network error
                    e.printStackTrace()
                } catch (e: HttpException) {
                    // Handle HTTP error (non-2xx)
                    e.printStackTrace()
                }
            }

            // Return products from the local database
            return@withContext productDao.getAllProducts().map { p -> p.asProductModel() }
        }
    }

    override suspend fun refreshDatabase() {
        productDao.clear()
    }


    override suspend fun getProductById(productId: String): ProductDetailModel {
        return withContext(Dispatchers.IO) {
            // Check for internet connectivity
            if (isInternetConnected()) {
                try {
                    // Perform API call
                    val productsFromApi = productApi.getProductById(productId)
                    val productEntity = productsFromApi.asProductDetailModel().asEntity()
                    
                    // Save results in the local database
                    productDao.insert(productEntity)
                } catch (e: IOException) {
                    // Handle network error
                    e.printStackTrace()
                } catch (e: HttpException) {
                    // Handle HTTP error (non-2xx)
                    e.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            var product = productDao.get(productId).asProductDetailModel()
            return@withContext product
        }
    }

    override suspend fun searchAllProductsByTitle(title: String): List<ProductModel> {
        return withContext(Dispatchers.IO) {
            // Check for internet connectivity
            if (isInternetConnected()) {
                try {
                    // Perform API call
                    val productsFromApi = productApi.getProducts(title)
                    var productEntities = productsFromApi.results.map { p -> p.asEntity() }
                    productEntities = productEntities.filter {
                        p -> productDao.get(p.productId).productId == p.productId
                    }
                    // Save results in the local database
                    productDao.insertAll(*productEntities.toTypedArray())
                } catch (e: IOException) {
                    // Handle network error
                    e.printStackTrace()
                } catch (e: HttpException) {
                    // Handle HTTP error (non-2xx)
                    e.printStackTrace()
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
            var products = productDao.getAllProductsByTitle(title).map { p -> p.asProductModel() }
            return@withContext products
        }
    }

    private fun isInternetConnected(): Boolean {

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

}