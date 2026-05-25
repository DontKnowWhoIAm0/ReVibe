package com.revibe.feature.favourites.data.dto

import java.util.UUID

data class FavouriteDto(
    val article: UUID,
    val name: String,
    val price: Int,
    val imageUrl: String?,
    val gender: String,
    val color: String,
    val brand: String,
    val size: String
)