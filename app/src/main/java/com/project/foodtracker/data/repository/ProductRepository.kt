package com.project.foodtracker.data.repository

 import android.content.Context
 import android.net.ConnectivityManager
 import android.net.NetworkCapabilities
 import com.project.foodtracker.data.database.dao.IProductDao
 import com.project.foodtracker.data.database.entities.asDomain
 import com.project.foodtracker.data.remote.IProductApiService
 import com.project.foodtracker.data.remote.dto.toProductEntity
 import com.project.foodtracker.domain.model.ProductDetailModel
 import com.project.foodtracker.domain.model.ProductModel
 import com.project.foodtracker.domain.repository.IProductRepository
 import kotlinx.coroutines.Dispatchers
 import kotlinx.coroutines.withContext
 import retrofit2.HttpException
 import java.io.IOException
 import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productDao: IProductDao,
    private val productApi: IProductApiService,
    private val context: Context
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

    override suspend fun searchAllProductsByTitle(title: String): List<ProductModel> {
        return withContext(Dispatchers.IO) {
            // Check for internet connectivity
            if (isInternetConnected()) {
                try {
                    // Perform API call
                    val productsFromApi = productApi.getProducts(title)
                    val productEntities = productsFromApi.products.map { p -> p.toProductEntity() }
                    // Save results in the local database
                    productDao.insertAll(*productEntities.toTypedArray())
                } catch (e: IOException) {
                    // Handle network error
                    e.printStackTrace()
                } catch (e: HttpException) {
                    // Handle HTTP error (non-2xx)
                    e.printStackTrace()
                }
            }

            // Return products from the local database
            return@withContext productDao.getAllProductsByTitle(title).map { p -> p.asDomain() }
        }
    }

    private fun isInternetConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

}