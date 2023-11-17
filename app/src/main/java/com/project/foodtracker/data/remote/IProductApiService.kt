package com.project.foodtracker.data.remote

import com.project.foodtracker.common.Constants
import com.project.foodtracker.data.remote.dto.product.ProductDto
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface IProductApiService {

    @Headers(
        "x-api-key: ${Constants.API_KEY}",
        "Accept: */*",
        )
    @GET("/food/products/search")
    suspend fun getProducts(@Query("number") number: Int = 30): Deferred<List<ProductDto>>

    @GET("/food/products/:id?")
    suspend fun getProductById(@Path("id") productId: String): ProductDto

    @GET("/food/products/suggest")
    suspend fun findProductByTitle(
        @Query("number") number: Int = 30,
        @Query("query") title: String
    ): Deferred<List<ProductDto>>
}