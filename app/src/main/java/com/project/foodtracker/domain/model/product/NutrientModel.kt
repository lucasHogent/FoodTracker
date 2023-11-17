package com.project.foodtracker.domain.model.product

import com.google.gson.annotations.SerializedName

data class NutrientModel(
    val name: String,
    val amount: Int,
    val unit: String,
    @SerializedName("percentOfDailyNeeds") val percentOfDailyNeeds: Double
)