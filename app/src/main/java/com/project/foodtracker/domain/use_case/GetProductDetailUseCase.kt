package com.project.foodtracker.domain.use_case

import com.project.foodtracker.common.Resource
import com.project.foodtracker.domain.model.ProductDetailModel
import com.project.foodtracker.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetProductDetailUseCase @Inject constructor(
    private val repository: IProductRepository
) {
    operator fun invoke(productId: String): Flow<Resource<ProductDetailModel>> = flow {
        try {
            emit(Resource.Loading<ProductDetailModel>())
            val coin = repository.getProductById(productId)
            emit(Resource.Success<ProductDetailModel>(coin))
        } catch(e: HttpException) {
            emit(Resource.Error<ProductDetailModel>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<ProductDetailModel>("Couldn't reach server. Check your internet connection."))
        }
    }
}