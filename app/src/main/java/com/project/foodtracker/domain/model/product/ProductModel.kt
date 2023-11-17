package com.project.foodtracker.domain.model.product
import com.google.gson.annotations.SerializedName


data class ProductModel(
    val id: String,
    val title: String,
    val breadcrumbs: List<String>,
    @SerializedName("imageType") val imageType: String,
    val badges: List<String>,
    @SerializedName("importantBadges") val importantBadges: List<String>,
    @SerializedName("ingredientCount") val ingredientCount: Int,
    @SerializedName("generatedText") val generatedText: String?,
    val likes: Int,
    val aisle: String,
    @SerializedName("spoonacularScore") val spoonacularScore: Int
)


