package com.project.foodtracker.domain.model.product

import com.google.gson.annotations.SerializedName
import com.project.foodtracker.data.database.entities.IngredientEntity

data class IngredientModel(
    val ingredientId: String,
    val description: String?,
    val name: String,
    @SerializedName("safety_level") val safetyLevel: String?
)

fun IngredientModel.asEntity(): IngredientEntity {
    return IngredientEntity(
        ingredientId = this.ingredientId,
        description = this.description,
        name = this.name,
        safetyLevel = this.safetyLevel
    )
}

// Extension function to convert List<IngredientModel> to List<IngredientEntity>
fun List<IngredientModel>.asEntity(): List<IngredientEntity> {
    return map { it.asEntity() }
}


