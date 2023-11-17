package com.project.foodtracker.domain.model.product

import com.google.gson.annotations.SerializedName

data class IngredientModel(
    val description: String?,
    val name: String,
    @SerializedName("safety_level") val safetyLevel: String?
)