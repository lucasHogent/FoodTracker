package com.project.foodtracker.domain.use_case

import com.project.foodtracker.common.Resource
import com.project.foodtracker.domain.model.ProductModel
import com.project.foodtracker.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * Use case for retrieving a list of products based on a query.
 *
 * @property repository The repository for managing product data.
 */
class GetProductsUseCase @Inject constructor(
    private val repository: IProductRepository
) {
    /**
     * Retrieves a list of products based on the provided query.
     *
     * @param query The search query to filter products.
     *
     * @return A [Flow] emitting [Resource] representing the state of the operation.
     *   - [Resource.Loading] when the operation is in progress.
     *   - [Resource.Success] with the list of [ProductModel] when successful.
     *   - [Resource.Error] with an error message when an unexpected error occurs.
     */
    operator fun invoke(query : String): Flow<Resource<List<ProductModel>>> = flow {
        try {
            emit(Resource.Loading<List<ProductModel>>())
            var products = emptyList<ProductModel>()
            if (query.isEmpty()) {
                products = repository.getAllProducts()
            } else {
                products = repository.searchAllProductsByTitle(query)
            }

            emit(Resource.Success<List<ProductModel>>(products))
        } catch (e: HttpException) {
            Timber.e(e.localizedMessage ?: "An unexpected error occured")
            emit(Resource.Error<List<ProductModel>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            Timber.e(e.localizedMessage ?: "Couldn't reach server. Check your internet connection.")
            emit(Resource.Error<List<ProductModel>>("Couldn't reach server. Check your internet connection."))
        }
    }
}