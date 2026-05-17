package com.revibe.feature.product_details.data.dto

import java.util.UUID

data class BranchDto(
    val id: UUID,
    val name: String,
    val city: String,
    val address: String
)