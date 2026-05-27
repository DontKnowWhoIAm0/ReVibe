package com.revibe.core.network.favourites

import java.util.UUID

data class FavouriteItemDto(
    val article: UUID,
    val name: String,
    val price: Int,
    val imageUrl: String?,
    val gender: String,
    val color: String,
    val brand: String,
    val size: String
)