package com.revibe.feature.product_details.domain.model

data class ProductDetail(
    val article: String,
    val name: String,
    val description: String?,
    val price: Int,
    val category: String?,
    val gender: String,
    val color: String,
    val brand: String,
    val size: String,
    val condition: String,
    val imageUrl: String?,
    val location: String
)