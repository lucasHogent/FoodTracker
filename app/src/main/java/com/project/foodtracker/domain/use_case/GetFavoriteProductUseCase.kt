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

class GetFavoriteProductUseCase @Inject constructor(
    private val repository: IFavoritesRepository
) {

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
