package com.project.foodtracker.data.remote

import com.project.foodtracker.common.Constants
import com.project.foodtracker.data.remote.dto.ProductApiResponse
import com.project.foodtracker.data.remote.dto.ProductDetailDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface IProductApiService {

    @Headers(
        "x-api-key: ${Constants.API_KEY}",
        "Accept: */*",
        )
    @GET("/recipes/complexSearch")
    suspend fun getProducts(
        @Query("query") title: String,
        @Query("number") number: Int = 2,
        @Query("offset") offset: Int = 0,): ProductApiResponse

    @Headers(
        "x-api-key: ${Constants.API_KEY}",
        "Accept: */*",
    )
    @GET("/recipes/{id}/information")
    suspend fun getProductById(@Path("id") id: String): ProductDetailDto

}