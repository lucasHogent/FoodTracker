package com.project.foodtracker.data.remote.dto.product

import com.project.foodtracker.domain.model.product.IngredientModel

data class IngredientDto(
    val description: String?,  // Change the type if needed
    val name: String,
    val safety_level: String?  // Change the type if needed
)

fun IngredientDto.toIngredientModel(): IngredientModel {
    return IngredientModel(
        description = this.description,
        name = this.name,
        safetyLevel = this.safety_level
    )
}