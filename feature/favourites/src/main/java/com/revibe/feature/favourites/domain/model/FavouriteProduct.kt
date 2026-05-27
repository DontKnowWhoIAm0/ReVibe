package com.revibe.feature.favourites.domain.model

import java.util.UUID

data class FavouriteProduct(
    val article: UUID,
    val name: String,
    val price: Int,
    val imageUrl: String?,
    val gender: String,
    val color: String,
    val brand: String,
    val size: String
)