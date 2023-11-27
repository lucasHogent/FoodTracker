package com.project.foodtracker.data.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.project.foodtracker.domain.model.product.IngredientModel
import java.util.UUID

@Entity(tableName = "ingredients",
    indices = [Index(name = "idxIngredientName", value = ["name"], unique = true)])
data class IngredientEntity(
    @PrimaryKey() val ingredientId: String,
    val description: String?,
    val name: String,
    val safetyLevel: String?
){

}

// Extension functions to convert IngredientEntity to Ingredient
fun IngredientEntity.asDomainModel(): IngredientModel {
    return IngredientModel(
        ingredientId = this.ingredientId,
        description = this.description,
        name = this.name,
        safetyLevel = this.safetyLevel
    )
}

// Extension functions to convert List<IngredientEntity> to List<Ingredient>
fun List<IngredientEntity>.asDomainModel(): List<IngredientModel> {
    return map { it.asDomainModel() }
}
