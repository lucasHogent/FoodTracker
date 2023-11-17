package com.project.foodtracker.data.remote.dto.product

import com.project.foodtracker.domain.model.product.CaloricBreakdownModel

data class CaloricBreakdownDto(
    val percentProtein: Double,
    val percentFat: Double,
    val percentCarbs: Double
)

fun CaloricBreakdownDto.toCaloricBreakdownModel(): CaloricBreakdownModel {
    return CaloricBreakdownModel(
        percentProtein = this.percentProtein,
        percentFat = this.percentFat,
        percentCarbs = this.percentCarbs
    )
}