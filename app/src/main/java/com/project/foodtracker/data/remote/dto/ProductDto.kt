package com.project.foodtracker.data.remote.dto

import com.project.foodtracker.domain.model.ProductDetailModel

data class ProductDto(
    val id: String,
    val title: String,
    val breadcrumbs: List<String>,
    val imageType: String,
    val badges: List<String>,
    val importantBadges: List<String>,
    val ingredientCount: Int,
    val generatedText: String?,
    val ingredients: List<IngredientDto>,
    val likes: Int,
    val aisle: String,
    val price: Double,
    val servings: ServingsDto,
    val spoonacularScore: Double
)
fun ProductDto.toModel(): ProductDetailModel {
    return ProductDetailModel(
        id  = id,
        title = title,
        breadcrumbs = breadcrumbs,
        imageType = imageType,
        badges = badges,
        importantBadges = importantBadges,
        ingredientCount = ingredientCount,
        generatedText = generatedText,
        likes = likes,
        aisle = aisle,
        price = price,
        servings = servings.toServingsModel(),
        spoonacularScore = spoonacularScore,
        ingredients = ingredients.toModelList()


    )
}

fun List<ProductDto>.toModelList(): List<ProductDetailModel> {
    return map { it.toModel() }
}