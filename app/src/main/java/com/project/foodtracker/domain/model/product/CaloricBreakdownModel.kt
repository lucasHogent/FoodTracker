package com.project.foodtracker.domain.model.product

import com.google.gson.annotations.SerializedName

data class CaloricBreakdownModel(
    @SerializedName("percentProtein") val percentProtein: Double,
    @SerializedName("percentFat") val percentFat: Double,
    @SerializedName("percentCarbs") val percentCarbs: Double
)