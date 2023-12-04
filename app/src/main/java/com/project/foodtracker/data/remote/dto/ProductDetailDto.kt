package com.project.foodtracker.data.remote.dto

import com.project.foodtracker.domain.model.ProductDetailModel


data class ProductDetailDto(
    val id: String,
    val title: String,
    val imageType: String,
    val image: String,
    val servings: Int,
    val readyInMinutes: Int,
    val license: String,
    val sourceName: String,
    val sourceUrl: String,
    val spoonacularSourceUrl: String,
    val healthScore: Float,
    val spoonacularScore: Float,
    val pricePerServing: Float,
    val cheap: Boolean,
    val creditsText: String,
    val dairyFree: Boolean,
    val gaps: String,
    val glutenFree: Boolean,
    val instructions: String,
    val ketogenic: Boolean,
    val lowFodmap: Boolean,
    val occasions: List<String>,
    val sustainable: Boolean,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val weightWatcherSmartPoints: Int,
    val dishTypes: List<String>,
    val summary: String
)

fun ProductDetailDto.asProductDetailModel(): ProductDetailModel {
    return ProductDetailModel(
        productId = this.id,
        title = this.title,
        image = this.image,
        imageType = this.imageType,
        servings = this.servings,
        readyInMinutes = this.readyInMinutes,
        license = this.license,
        sourceName = this.sourceName,
        sourceUrl = this.sourceUrl,
        spoonacularSourceUrl = this.spoonacularSourceUrl,
        healthScore = this.healthScore,
        spoonacularScore = this.spoonacularScore,
        pricePerServing = this.pricePerServing,
        cheap = this.cheap,
        creditsText = this.creditsText,
        dairyFree = this.dairyFree,
        gaps = this.gaps,
        glutenFree = this.glutenFree,
        instructions = this.instructions,
        ketogenic = this.ketogenic,
        lowFodmap = this.lowFodmap,
        occasions = this.occasions,
        sustainable = this.sustainable,
        vegan = this.vegan,
        vegetarian = this.vegetarian,
        veryHealthy = this.veryHealthy,
        veryPopular = this.veryPopular,
        weightWatcherSmartPoints = this.weightWatcherSmartPoints,
        dishTypes = this.dishTypes,
        summary = this.summary
    )
}


fun List<ProductDetailDto>.toModelList(): List<ProductDetailModel> {
    return map { it.asProductDetailModel() }
}