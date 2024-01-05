package com.project.foodtracker.domain.model

import com.project.foodtracker.data.database.entities.ProductEntity


data class ProductDetailModel(

    val productId: String,
    val title: String,
    val image: String,
    val imageType: String,
    val servings: Int,
    val readyInMinutes: Int,
    val license: String?,
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
    val summary: String,
)

fun ProductDetailModel.asEntity(): ProductEntity {
    return ProductEntity(
        productId = this.productId,
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
        summary = this.summary,
        deleted = false
    )
}

fun ProductDetailModel.asProductModel(): ProductModel {
    return ProductModel(
        id = this.productId,
        title = this.title,
        image = this.image,
        imageType = this.imageType
    )
}

// Extension function to convert List<ProductModel> to List<ProductWithIngredientsAnsNutritionAndServingsEntity>
fun List<ProductDetailModel>.asEntity(): List<ProductEntity> {
    return map { it.asEntity() }
}


