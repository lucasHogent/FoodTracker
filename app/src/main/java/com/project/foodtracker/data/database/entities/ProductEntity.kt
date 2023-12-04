package com.project.foodtracker.data.database.entities


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.project.foodtracker.data.database.StringListConverter
import com.project.foodtracker.domain.model.ProductDetailModel
import com.project.foodtracker.domain.model.ProductModel

@Entity(
    tableName = "products",
    indices = [Index(name = "idxProductTitle", value = ["title"], unique = true)]
)
data class ProductEntity(
    @PrimaryKey
    val productId: String,

    val title: String,
    val image: String,

    @SerializedName("imageType")
    val imageType: String,

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

    @TypeConverters(StringListConverter::class)
    val occasions: List<String>,

    val sustainable: Boolean,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val weightWatcherSmartPoints: Int,

    @TypeConverters(StringListConverter::class)
    val dishTypes: List<String>,

    val summary: String,

    )

fun ProductEntity.asProductDetailModel(): ProductDetailModel {
    return ProductDetailModel(
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
        summary = this.summary
    )
}

// Extension function to convert List<ProductEntity> to List<ProductModel>
fun List<ProductEntity>.asProductDetailModel(): List<ProductDetailModel> {
    return map { it.asProductDetailModel() }
}

fun ProductEntity.asProductModel(): ProductModel {
    return ProductModel(
        id = this.productId,
        title = this.title,
        imageType = this.imageType,
        image = this.image,
    )
}

fun List<ProductEntity>.asProductModel(): List<ProductModel> {
    return map { it.asProductModel() }
}




