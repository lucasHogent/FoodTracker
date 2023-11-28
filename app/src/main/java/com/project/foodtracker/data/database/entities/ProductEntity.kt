package com.project.foodtracker.data.database.entities


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.project.foodtracker.data.database.StringListConverter
import com.project.foodtracker.domain.model.ProductModel

@Entity(tableName = "products",
    indices = [Index(name = "idxProductName", value = ["title"], unique = true)])
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
    val price: Double,
    @ColumnInfo(name = "spoonacular_score") val spoonacularScore: Double,
    @Embedded val servings: ServingsEntity,

)

fun ProductEntity.asDomain(): ProductModel {
    return ProductModel(
        id = this.productId,
        title = this.title,
        imageType = this.imageType,
    )
}

// Extension function to convert List<ProductEntity> to List<ProductModel>
fun List<ProductEntity>.asDomain(): List<ProductModel> {
    return map { it.asDomain() }
}

