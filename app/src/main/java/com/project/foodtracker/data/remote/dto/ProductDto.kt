package com.project.foodtracker.data.remote.dto

import com.project.foodtracker.data.database.entities.ProductEntity


data class ProductDto(
    val id: String,
    val title: String,
    val imageType: String,
    val image: String
)
data class ProductApiResponse(
    val results: List<ProductDto>,
    val totalResults: Int,
    val type: String,
    val offset: Int,
    val number: Int
)

fun ProductDto.asEntity(): ProductEntity {
    return ProductEntity(
        productId = this.id,
        title = this.title,
        image = this.image,
        imageType = this.imageType,
        servings = 0,
        readyInMinutes = 0,
        license = "",
        sourceName = "",
        sourceUrl = "",
        spoonacularSourceUrl = "",
        healthScore = 0.0f,
        spoonacularScore = 0.0f,
        pricePerServing = 0.0f,
        cheap = false,
        creditsText = "",
        dairyFree = false,
        gaps = "",
        glutenFree = false,
        instructions = "",
        ketogenic = false,
        lowFodmap = false,
        occasions = emptyList(),
        sustainable = false,
        vegan = false,
        vegetarian = false,
        veryHealthy = false,
        veryPopular = false,
        weightWatcherSmartPoints = 0,
        dishTypes = emptyList(),
        summary = "",
        deleted = false
    )
}


