package com.project.foodtracker.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "favorite_product_table",
    foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = ["id"],
        childColumns = ["product_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("product_id")]
)
data class FavoriteProduct(
    @PrimaryKey(autoGenerate = false)
    var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "product_id")
    val productId: String,
    @ColumnInfo(name = "create_timestamp")
    val createTimeStamp: String
) {}