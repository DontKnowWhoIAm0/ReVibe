package com.revibe.feature.catalog.domain.model

import java.util.UUID

data class Product(
    val article: UUID,
    val name: String,
    val price: Int,
    val imageUrl: String?,
    val gender: String,
    val color: String,
    val brand: String,
    val size: String,
    val condition: String,
    val category: String,
    val branchId: String,
    val branchAddress: String
)
