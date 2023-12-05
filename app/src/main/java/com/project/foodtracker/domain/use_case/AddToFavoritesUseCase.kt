package com.project.foodtracker.domain.use_case

import com.project.foodtracker.common.Resource
import com.project.foodtracker.data.database.entities.FavoriteWithProduct
import com.project.foodtracker.domain.model.ProductDetailModel
import com.project.foodtracker.domain.repository.IFavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
    private val repository: IFavoritesRepository
) {
    suspend operator fun invoke(productId: String) {
        repository.addProductToFavorites(productId)
    }
}

