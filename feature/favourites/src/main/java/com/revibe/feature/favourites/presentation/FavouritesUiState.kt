package com.revibe.feature.favourites.presentation

import com.revibe.feature.favourites.domain.model.FavouriteProduct

data class FavouritesUiState(
    val products: List<FavouriteProduct> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)