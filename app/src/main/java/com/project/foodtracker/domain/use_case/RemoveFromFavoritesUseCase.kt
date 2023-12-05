package com.project.foodtracker.domain.use_case

import com.project.foodtracker.domain.repository.IFavoritesRepository
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(
    private val repository: IFavoritesRepository
) {
    suspend operator fun invoke(productId: String) {
        repository.deleteFavorite(productId)
    }
}