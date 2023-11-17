package com.project.foodtracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.foodtracker.domain.model.ProductModel
import java.util.UUID


@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    )

fun List<Product>.asDomainModel(): List<ProductModel> {
    return map {
        it.asDomainModel()
    }
}

fun Product.asDomainModel(): ProductModel {
    return ProductModel(
        id = this.id,
        name = this.name,
        description = this.description,
        price = this.price,
        image = this.imageUrl,
    )
}
