package com.project.foodtracker.domain.use_case

import com.project.foodtracker.common.Resource
import com.project.foodtracker.domain.model.ProductModel
import com.project.foodtracker.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: IProductRepository
) {
    operator fun invoke(query : String): Flow<Resource<List<ProductModel>>> = flow {
        try {
            emit(Resource.Loading<List<ProductModel>>())
            var products = emptyList<ProductModel>()
            if (query.isEmpty()) {
                products = repository.getAllProducts()
            } else {
                products = repository.searchAllProductsByTitle(query)
            }

            emit(Resource.Success<List<ProductModel>>(products))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<ProductModel>>(
                    e.localizedMessage ?: "An unexpected error occured"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<List<ProductModel>>("Couldn't reach server. Check your internet connection."))
        }
    }
}