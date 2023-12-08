package com.project.foodtracker.domain.use_case

import com.project.foodtracker.common.Resource
import com.project.foodtracker.domain.model.ProductModel
import com.project.foodtracker.domain.repository.IFavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: IFavoritesRepository
) {
    operator fun invoke(): Flow<Resource<List<ProductModel>>> = flow {
        try {
            emit(Resource.Loading<List<ProductModel>>())
            var products = emptyList<ProductModel>()
                products = repository.getAllFavoriteProducts()

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
