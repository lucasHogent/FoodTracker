package com.project.foodtracker.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class FavoriteWithProduct(
    @Embedded val product: ProductEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "productId"
    )
    val favorites: FavoriteEntity
)