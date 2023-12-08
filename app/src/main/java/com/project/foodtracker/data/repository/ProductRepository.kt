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
 import timber.log.Timber
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
                    val productsFromApi = productApi.getProducts("", 10)
                    Timber.i("Get products from API getProducts() %s" , productsFromApi.results.toTypedArray().contentToString())
                    val filteredProductEntities = productsFromApi.results
                        .map { it.asEntity() }

                    productDao.insertAll(*filteredProductEntities.toTypedArray())
                    Timber.i("Insert products from API to productDao.insertAll %s" , filteredProductEntities.toTypedArray().contentToString())
                } catch (e: IOException) {
                    Timber.e(e.message)
                } catch (e: HttpException) {
                    Timber.e(e.message)
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
                    Timber.i("Get products from API getProductById() %s" , productsFromApi)
                    // Save results in the local database
                    productDao.insert(productEntity)
                    Timber.i("Insert product from API to productDao.insert %s" , productEntity)
                } catch (e: IOException) {
                    Timber.e(e.message)
                } catch (e: HttpException) {
                    Timber.e(e.message)
                } catch (e: Exception) {
                    Timber.e(e.message)
                }
            }
            val product = productDao.get(productId).asProductDetailModel()
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
                    Timber.i("Get products from API getProducts(%s) " , title)
                    Timber.i("Get products from API getProducts(title) %s" , productsFromApi.results.toTypedArray())

                    val productEntities = productsFromApi.results.map { p -> p.asEntity() }

                    val newProducts = productEntities.filterNot { apiProduct ->
                        val dbProduct = productDao.get(apiProduct.productId)
                        dbProduct != null && dbProduct.title == apiProduct.title
                    }

                    // Save results in the local database
                    productDao.insertAll(*newProducts.toTypedArray())
                    Timber.i("Insert products from API to productDao.insertAll %s" , productEntities.toTypedArray())
                } catch (e: IOException) {
                    Timber.e(e.message)
                } catch (e: HttpException) {
                    Timber.e(e.message)
                } catch (e: Exception){
                    Timber.e(e.message)
                }
            }
            val products = productDao.getAllProductsByTitle(title).map { p -> p.asProductModel() }
            return@withContext products
        }
    }

    private fun isInternetConnected(): Boolean {

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        Timber.i("Internet is connected: %b", capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

}