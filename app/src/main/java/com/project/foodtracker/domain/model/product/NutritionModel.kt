package com.project.foodtracker.domain.model.product

import com.google.gson.annotations.SerializedName

data class NutritionModel(
    val nutrients: List<NutrientModel>,
    @SerializedName("caloricBreakdown") val caloricBreakdown: CaloricBreakdownModel
)