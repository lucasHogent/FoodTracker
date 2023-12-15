package com.project.foodtracker.domain.use_case

import com.project.foodtracker.common.Resource
import com.project.foodtracker.domain.model.ProductDetailModel
import com.project.foodtracker.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * Use case for retrieving detailed information about a product.
 *
 * @property repository The repository for managing product data.
 */
class GetProductDetailUseCase @Inject constructor(
    private val repository: IProductRepository
) {
    /**
     * Retrieves detailed information about a product by its ID.
     *
     * @param productId The unique identifier of the product.
     *
     * @return A [Flow] emitting [Resource] representing the state of the operation.
     *   - [Resource.Loading] when the operation is in progress.
     *   - [Resource.Success] with the [ProductDetailModel] when successful.
     *   - [Resource.Error] with an error message when an unexpected error occurs.
     */
    operator fun invoke(productId: String): Flow<Resource<ProductDetailModel>> = flow {
        try {
            emit(Resource.Loading<ProductDetailModel>())
            val product = repository.getProductById(productId)
            emit(Resource.Success<ProductDetailModel>(product))
        } catch(e: HttpException) {
            Timber.e(e.localizedMessage ?: "An unexpected error occured")
            emit(Resource.Error<ProductDetailModel>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            Timber.e(e.localizedMessage ?: "Couldn't reach server. Check your internet connection.")
            emit(Resource.Error<ProductDetailModel>("Couldn't reach server. Check your internet connection."))
        }
    }
}