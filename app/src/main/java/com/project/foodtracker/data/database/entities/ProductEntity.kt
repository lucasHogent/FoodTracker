package com.project.foodtracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.foodtracker.domain.model.product.ProductModel


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.TypeConverters
import com.project.foodtracker.data.database.StringListConverter
import com.project.foodtracker.domain.model.product.CaloricBreakdownModel
import com.project.foodtracker.domain.model.product.IngredientModel
import com.project.foodtracker.domain.model.product.NutrientModel
import com.project.foodtracker.domain.model.product.NutritionModel
import com.project.foodtracker.domain.model.product.ServingsModel

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val productId: String,
    val title: String,

    @TypeConverters(StringListConverter::class)
    val breadcrumbs: List<String>,

    @ColumnInfo(name = "image_type") val imageType: String,
    @TypeConverters(StringListConverter::class)
    val badges: List<String>,

    @ColumnInfo(name = "important_badges")
    @TypeConverters(StringListConverter::class)
    val importantBadges: List<String>,

    @ColumnInfo(name = "ingredient_count") val ingredientCount: Int,
    @ColumnInfo(name = "generated_text") val generatedText: String?,

    val likes: Int,
    val aisle: String,

    @ColumnInfo(name = "spoonacular_score") val spoonacularScore: Int
)

@Entity(tableName = "nutrients")
data class NutrientEntity(
    @PrimaryKey val nutrientId: String,
    val name: String,
    val amount: Int,
    val unit: String,
    @ColumnInfo(name = "percent_of_daily_needs") val percentOfDailyNeeds: Double
)

@Entity(tableName = "ingredients")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val ingredientId: Int, // Foreign key to associate with ProductEntity
    val description: String?,
    val name: String,
    val safetyLevel: String?
)


data class ProductEntityAndNutritionEntity(
    @Embedded val productEntity: ProductEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "nutritionId"
    )
    val nutritionEntity: NutritionEntity
)

data class ProductEntityWithIngredientEntities(
    @Embedded val productEntity: ProductEntity,
    @Relation(entity = IngredientEntity::class,
        parentColumn = "productId",
        entityColumn = "ingredientId")
    val ingredientList: List<IngredientEntity>,
)

data class NutritionEntity(
    @Embedded val caloricBreakdown: CaloricBreakdownEntity
)


data class NutritionEntityWithNutrientEntities(
    @Embedded val nutrition : NutritionEntity,
    @Relation(parentColumn = "nutritionId",
        entityColumn = "nutrientId")
    val nutrients: List<NutrientEntity>,
)



data class CaloricBreakdownEntity(
    @ColumnInfo(name = "percent_protein") val percentProtein: Double,
    @ColumnInfo(name = "percent_fat") val percentFat: Double,
    @ColumnInfo(name = "percent_carbs") val percentCarbs: Double
)

data class ServingsEntity(
    val number: Int,
    val size: Int,
    val unit: String
)

fun List<ProductEntity>.asDomainModel(): List<ProductModel> {
    return map {
        it.asDomainModel()
    }
}

fun ProductEntity.asDomainModel(): ProductModel {
    return ProductModel(
        id = productId,
        title = title,
        breadcrumbs = breadcrumbs,
        imageType = imageType,
        badges = badges,
        importantBadges = importantBadges,
        ingredientCount = ingredientCount,
        generatedText = generatedText,
        likes = likes,
        aisle = aisle,
        spoonacularScore = spoonacularScore
    )

}


