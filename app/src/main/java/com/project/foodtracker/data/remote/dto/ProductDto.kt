package com.project.foodtracker.data.remote.dto

import com.project.foodtracker.data.database.entities.ProductEntity
import com.project.foodtracker.data.database.entities.ServingsEntity
import com.project.foodtracker.domain.model.ProductModel


data class ProductDto(
    val id: String,
    val title: String,
    val imageType: String
)
data class ProductApiResponse(
    val products: List<ProductDto>,
    val totalProducts: Int,
    val type: String,
    val offset: Int,
    val number: Int
)

fun ProductDto.toProductEntity(): ProductEntity {
    return ProductEntity(
        productId = this.id,
        title = this.title,
        breadcrumbs = emptyList(),
        imageType = this.imageType,
        badges = emptyList(),
        importantBadges = emptyList(),
        ingredientCount = 0,
        generatedText = null,
        likes = 0,
        aisle = "",
        price = 0.0,
        spoonacularScore = 0.0,
        servings = ServingsEntity(0, 0, "")
    )
}