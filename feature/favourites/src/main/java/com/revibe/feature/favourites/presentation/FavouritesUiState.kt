package com.revibe.feature.favourites.presentation

import com.revibe.feature.favourites.domain.model.FavouriteProduct
import java.util.UUID

data class FavouritesUiState(
    val products: List<FavouriteProduct> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val cartArticles: Set<UUID> = emptySet(),
)