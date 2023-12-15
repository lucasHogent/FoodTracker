package com.project.foodtracker.domain.use_case

import com.project.foodtracker.domain.repository.IFavoritesRepository
import javax.inject.Inject

/**
 * Use case for removing a product from the list of favorites.
 *
 * @property repository The repository for managing favorite products.
 */
class RemoveFromFavoritesUseCase @Inject constructor(
    private val repository: IFavoritesRepository
) {
    /**
     * Removes a product from the list of favorites.
     *
     * @param productId The unique identifier of the product to be removed from favorites.
     */
    suspend operator fun invoke(productId: String) {
        repository.deleteFavorite(productId)
    }
}