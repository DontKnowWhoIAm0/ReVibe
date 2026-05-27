package com.revibe.feature.product_details.presentation

data class ProductUiState(
    val article: String = "",
    val title: String = "",
    val brand: String = "",
    val price: String = "",
    val size: String = "",
    val condition: String = "",
    val gender: String = "",
    val location: String = "",
    val imageUrl: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isFavourite: Boolean = false,
    val isInCart: Boolean = false,
    val favouriteError: String? = null

)
