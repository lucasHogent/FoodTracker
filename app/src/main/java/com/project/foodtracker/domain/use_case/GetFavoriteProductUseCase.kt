package com.project.foodtracker.domain.use_case

import com.project.foodtracker.common.Resource
import com.project.foodtracker.data.database.entities.FavoriteWithProduct
import com.project.foodtracker.domain.model.ProductDetailModel
import com.project.foodtracker.domain.model.ProductModel
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

    operator fun invoke(productId : String): Flow<Resource<FavoriteWithProduct>> = flow {
        try {
            emit(Resource.Loading<FavoriteWithProduct>())

            var favorite = repository.getFavoriteProduct(productId)

            emit(Resource.Success<FavoriteWithProduct>(favorite))
        } catch (e: HttpException) {
            Timber.e(e.localizedMessage ?: "An unexpected error occured")
            emit(Resource.Error<FavoriteWithProduct>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            Timber.e(e.localizedMessage ?: "Couldn't reach server. Check your internet connection.")
            emit(Resource.Error<FavoriteWithProduct>("Couldn't reach server. Check your internet connection."))
        }
    }
}
