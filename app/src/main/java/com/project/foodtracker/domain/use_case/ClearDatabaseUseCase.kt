package com.project.foodtracker.domain.use_case

import com.project.foodtracker.domain.repository.IFavoritesRepository
import com.project.foodtracker.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * Use case for clearing the database, including both favorites and products.
 *
 * @property favoriteRepository The repository for managing favorites data.
 * @property productRepository The repository for managing product data.
 */
class ClearDatabaseUseCase @Inject constructor(
    private val favoriteRepository: IFavoritesRepository,
    private val productRepository: IProductRepository) {

    /**
    * Invokes the use case to clear the database.
    *
    * @return A [Flow] emitting `true` if the database is cleared successfully.
    */
    operator fun invoke(): Flow<Boolean> = flow {
        try {
            productRepository.clear()
            favoriteRepository.clear()
            emit(true)
        } catch (e: HttpException) {
            Timber.e(e.localizedMessage ?: "An unexpected error occured")
        } catch (e: IOException) {
            Timber.e(e.localizedMessage ?: "Couldn't reach server. Check your internet connection.")
        }
    }

}