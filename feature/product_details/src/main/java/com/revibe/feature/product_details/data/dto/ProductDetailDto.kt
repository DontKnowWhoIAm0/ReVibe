package com.revibe.feature.product_details.data.dto

import java.util.UUID

data class ProductDetailDto(
    val article: UUID,
    val name: String,
    val description: String?,
    val price: Int,
    val category: String?,
    val gender: String?,
    val color: String?,
    val brand: String?,
    val size: String?,
    val condition: String?,
    val sold: Boolean,
    val imageUrl: String?,
    val branch: BranchDto?
)