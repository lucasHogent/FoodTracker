package com.project.foodtracker.data.remote.dto.product

import com.project.foodtracker.domain.model.product.ServingsModel

data class ServingsDto(
    val number: Int,
    val size: Int,
    val unit: String
)

fun ServingsDto.toServingsModel(): ServingsModel {
    return ServingsModel(
        number = this.number,
        size = this.size,
        unit = this.unit
    )
}
