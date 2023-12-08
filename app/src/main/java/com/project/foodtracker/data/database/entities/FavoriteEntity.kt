package com.project.foodtracker.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites",
    foreignKeys = [ForeignKey(
        entity = ProductEntity::class,
        parentColumns = ["productId"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class FavoriteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val productId: String,
)