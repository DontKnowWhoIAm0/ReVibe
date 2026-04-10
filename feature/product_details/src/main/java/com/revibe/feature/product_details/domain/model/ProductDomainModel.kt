package com.revibe.feature.product_details.domain.model

data class ProductDomainModel(
    val title: String,
    val brand: String,
    val priceFormatted: String,
    val article: String,
    val size: String,
    val condition: String,
    val location: String,
    val gender: String,
    val imageUrl: String
)
