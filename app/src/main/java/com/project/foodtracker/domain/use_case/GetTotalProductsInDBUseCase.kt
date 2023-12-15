package com.project.foodtracker.domain.use_case

 import com.project.foodtracker.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * Use case for retrieving the total count of products in the local database.
 *
 * @property repository The repository for managing product data in the local database.
 */
class GetTotalProductsInDBUseCase @Inject constructor(
    private val repository: IProductRepository
) {
    /**
     * Retrieves the total count of products in the local database.
     *
     * @return A [Flow] emitting the total count of products in the local database.
     *   - Emits the count of products when successful.
     *   - Logs an error message when an unexpected error occurs.
     */
    operator fun invoke(): Flow<Int> = flow {
        try {
            val totalProductsCount = repository.getCountDBProducts()
            emit(totalProductsCount)

        } catch (e: IOException) {
            Timber.e(e.localizedMessage ?: "Error getting from DB")
        }
    }
}