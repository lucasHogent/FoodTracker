package com.project.foodtracker.data.database.wrapper

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.project.foodtracker.data.database.entities.IngredientEntity
import com.project.foodtracker.data.database.entities.ProductEntity
import com.project.foodtracker.data.database.entities.ProductWithIngredientsCrossRef

data class ProductWrapper(
    @Embedded
    var productEntity: ProductEntity,
    @Relation(
        entity = IngredientEntity::class,
        parentColumn = "productId",
        entityColumn = "ingredientId",
        associateBy = (
                Junction(
                    value = ProductWithIngredientsCrossRef::class,
                    parentColumn = "productId",
                    entityColumn = "ingredientId"
                )
                )
    )
    var ingredients: List<IngredientEntity>,

)

