package com.project.foodtracker.domain.use_case

import com.project.foodtracker.common.Resource
import com.project.foodtracker.domain.model.ProductDetailModel
import com.project.foodtracker.domain.repository.IFavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * Use case for retrieving a favorite product by its ID.
 *
 * @property repository The repository for managing favorites data.
 */
class GetFavoriteProductUseCase @Inject constructor(
    private val repository: IFavoritesRepository
) {

    /**
     * Retrieves a favorite product by its ID.
     *
     * @param productId The ID of the favorite product to retrieve.
     * @return A [Flow] emitting [Resource] representing the state of the operation.
     *   - [Resource.Loading] when the operation is in progress.
     *   - [Resource.Success] with the retrieved [ProductDetailModel] when successful.
     *   - [Resource.Error] with an error message when an unexpected error occurs.
     */
    operator fun invoke(productId : String): Flow<Resource<ProductDetailModel>> = flow {
        try {
            emit(Resource.Loading<ProductDetailModel>())

            var favorite = repository.getFavoriteProduct(productId)

            emit(Resource.Success<ProductDetailModel>(favorite))
        } catch (e: HttpException) {
            Timber.e(e.localizedMessage ?: "An unexpected error occured")
            emit(Resource.Error<ProductDetailModel>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            Timber.e(e.localizedMessage ?: "Couldn't reach server. Check your internet connection.")
            emit(Resource.Error<ProductDetailModel>("Couldn't reach server. Check your internet connection."))
        }
    }
}
