package com.project.foodtracker.domain.use_case

import com.project.foodtracker.domain.repository.IFavoritesRepository
import javax.inject.Inject

/**
 * Use case for adding a product to favorites.
 *
 * @property repository The repository to interact with favorites data.
 */
class AddToFavoritesUseCase @Inject constructor(
    private val repository: IFavoritesRepository
) {
    /**
     * Invokes the use case to add a product to favorites.
     *
     * @param productId The ID of the product to be added to favorites.
     */
    suspend operator fun invoke(productId: String) {
        repository.addProductToFavorites(productId)
    }
}

