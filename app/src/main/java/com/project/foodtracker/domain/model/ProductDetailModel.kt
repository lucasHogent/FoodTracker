package com.project.foodtracker.domain.model

import com.project.foodtracker.data.database.entities.ProductEntity


data class ProductDetailModel(
    val id: String,
    val title: String,
    val breadcrumbs: List<String>,
    val imageType: String,
    val badges: List<String>,
    val importantBadges: List<String>,
    val ingredientCount: Int,
    val generatedText: String?,
    val ingredients: List<IngredientModel>,
    val likes: Int,
    val aisle: String,
    val price: Double,
    val servings: ServingsModel,
    val spoonacularScore: Double
)

data class ProductModel(
    val id: String,
    val title: String,
    val imageType: String
)

fun ProductDetailModel.asEntity(): ProductEntity {
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
        spoonacularScore = this.spoonacularScore,
        price = this.price,
        servings = this.servings.asEntity(),
    )

}

// Extension function to convert List<ProductModel> to List<ProductWithIngredientsAnsNutritionAndServingsEntity>
fun List<ProductDetailModel>.asEntity(): List<ProductEntity> {
    return map { it.asEntity() }
}


