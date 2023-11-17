package com.project.foodtracker.data.remote.dto.product

import com.project.foodtracker.domain.model.product.NutritionModel

data class NutritionDto(
    val nutrients: List<NutrientDto>,
    val caloricBreakdown: CaloricBreakdownDto
)

fun NutritionDto.toNutritionModel(): NutritionModel {
    return NutritionModel(
        nutrients = this.nutrients.map { it.toNutrientModel() },
        caloricBreakdown = this.caloricBreakdown.toCaloricBreakdownModel()
    )
}