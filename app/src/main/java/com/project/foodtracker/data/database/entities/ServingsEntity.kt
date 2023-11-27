package com.project.foodtracker.data.database.entities

import com.project.foodtracker.domain.model.ServingsModel

data class ServingsEntity(
    val number: Int,
    val size: Int,
    val unit: String
)

// Extension function to convert ServingsEntity to Servings
fun ServingsEntity.asDomainModel(): ServingsModel {
    return ServingsModel(
        number = this.number,
        size = this.size,
        unit = this.unit
    )
}