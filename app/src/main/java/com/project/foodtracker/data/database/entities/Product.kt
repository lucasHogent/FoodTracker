package com.project.foodtracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "product_table")
data class Product (
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString(),

    val name: String,
){
}