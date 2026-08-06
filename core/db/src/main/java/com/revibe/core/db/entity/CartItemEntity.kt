package com.revibe.core.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val article: String,
    val name: String,
    val price: Int,
    val imageUrl: String?,
    val brand: String,
    val size: String,
    val category: String,
    val condition: String,
    val branchId: String,
    val branchAddress: String
)
