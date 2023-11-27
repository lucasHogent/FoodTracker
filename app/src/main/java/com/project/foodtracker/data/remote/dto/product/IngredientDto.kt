package com.project.foodtracker.data.remote.dto.product

import com.project.foodtracker.domain.model.product.IngredientModel

data class IngredientDto(
    val ingredientId: String,
    val description: String?,
    val name: String,
    val safety_level: String?
)

fun IngredientDto.toModel(): IngredientModel {
    return IngredientModel(
        ingredientId = this.ingredientId,
        description = this.description,
        name = this.name,
        safetyLevel = this.safety_level
    )
}

fun List<IngredientDto>.toModelList(): List<IngredientModel> {
    return map { it.toModel() }
}