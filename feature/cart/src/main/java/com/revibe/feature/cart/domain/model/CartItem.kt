package com.revibe.feature.cart.domain.model

data class CartItem(
    val article: String,
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
