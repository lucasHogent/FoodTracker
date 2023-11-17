package com.project.foodtracker.domain.usecase

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.project.foodtracker.common.Resource
import com.project.foodtracker.domain.model.ProductModel
import com.project.foodtracker.domain.repository.IProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ProductDetailUseCase @Inject constructor(
    private val _productRepository : IProductRepository
){
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    operator fun invoke(productId : String): Flow<Resource<ProductModel>> = flow {
        try{
            emit(Resource.Loading())
            val products = _productRepository.getProductById(productId)
            emit(Resource.Success(products.first()))
        } catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected HTTP error occurred!"))
        } catch (e: IOException){
            emit(Resource.Error(e.localizedMessage ?: "Server unreachable!"))
        }
    }
}