package com.revibe.feature.catalog.data.dto

import java.util.UUID

data class ProductDto(
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
    val branch: BranchDto?
)

data class BranchDto(
    val id: String,
    val name: String,
    val city: String,
    val address: String
)
