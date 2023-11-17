package com.project.foodtracker.data.remote.dto.product

import com.project.foodtracker.domain.model.product.NutrientModel

data class NutrientDto(
    val name: String,
    val amount: Int,
    val unit: String,
    val percentOfDailyNeeds: Double
)

fun NutrientDto.toNutrientModel(): NutrientModel {
    return NutrientModel(
        name = this.name,
        amount = this.amount,
        unit = this.unit,
        percentOfDailyNeeds = this.percentOfDailyNeeds
    )
}