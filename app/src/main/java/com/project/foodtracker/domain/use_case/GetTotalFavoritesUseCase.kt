package com.project.foodtracker.domain.use_case

 import com.project.foodtracker.domain.repository.IFavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * Use case for retrieving the total count of favorite products.
 *
 * @property repository The repository for managing favorite product data.
 */
class GetTotalFavoritesUseCase @Inject constructor(
    private val repository: IFavoritesRepository
) {
    /**
     * Retrieves the total count of favorite products.
     *
     * @return A [Flow] emitting the total count of favorite products.
     *   - Emits the count of favorite products when successful.
     *   - Logs an error message when an unexpected error occurs.
     */
    operator fun invoke(): Flow<Int> = flow {

        try {

            val totalFavoritesCount = repository.getAllFavoriteProducts().count()
            emit(totalFavoritesCount)

        } catch (e: IOException) {
            Timber.e(e.localizedMessage ?: "Error getting from DB")
        }
    }
}