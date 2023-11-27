package com.project.foodtracker.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["productId", "ingredientId"],
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["productId"],
            childColumns = ["productId"]
        ), ForeignKey(
            entity = IngredientEntity::class,
            parentColumns = ["ingredientId"],
            childColumns = ["ingredientId"]
        )
    ]
)
data class ProductWithIngredientsCrossRef(
    var productId: String,
    @ColumnInfo(index = true) var ingredientId: String,

)




