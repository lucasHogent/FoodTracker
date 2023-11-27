package com.project.foodtracker.domain.model.product

import com.project.foodtracker.data.database.entities.ServingsEntity

data class ServingsModel(
    val number: Int,
    val size: Int,
    val unit: String
)

// Extension function to convert Servings to ServingsEntity
fun ServingsModel.asEntity(): ServingsEntity {
    return ServingsEntity(
        number = this.number,
        size = this.size,
        unit = this.unit
    )
}