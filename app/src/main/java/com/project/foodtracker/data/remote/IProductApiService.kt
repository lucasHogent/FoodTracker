package com.project.foodtracker.data.remote

import com.project.foodtracker.common.Constants
import com.project.foodtracker.data.remote.dto.ProductDto
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
    suspend fun getProducts(
        @Query("query") title: String,
        @Query("number") number: Int = 30,
        @Query("offset") offset: Int = 0,): Deferred<List<ProductDto>>

    @GET("/food/products/:id?")
    suspend fun getProductById(@Path("id") productId: String): ProductDto

}