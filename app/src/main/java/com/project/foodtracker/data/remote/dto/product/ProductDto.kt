package com.project.foodtracker.data.remote.dto.product

import com.project.foodtracker.data.database.entities.ProductEntity
import com.project.foodtracker.domain.model.product.CaloricBreakdownModel
import com.project.foodtracker.domain.model.product.IngredientModel
import com.project.foodtracker.domain.model.product.NutrientModel
import com.project.foodtracker.domain.model.product.NutritionModel
import com.project.foodtracker.domain.model.product.ProductModel
import com.project.foodtracker.domain.model.product.ServingsModel

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
    val nutrition: NutritionDto,
    val price: Int,
    val servings: ServingsDto,
    val spoonacularScore: Int
)

fun ProductDto.toProductEntity(): ProductEntity {
    return ProductEntity(
        productId = this.id,
        title = this.title,
        breadcrumbs = this.breadcrumbs,
        imageType = this.imageType,
        badges = this.badges,
        importantBadges = this.importantBadges,
        ingredientCount = this.ingredientCount,
        generatedText = this.generatedText,
        likes = this.likes,
        aisle = this.aisle,
        spoonacularScore = this.spoonacularScore
    )
}

