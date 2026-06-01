package com.revibe.feature.cart.presentation

import com.revibe.feature.cart.domain.model.CartItem

data class CartUiState(
    val itemsByBranch: Map<String, List<CartItem>> = emptyMap(),
    val isLoading: Boolean = false,
    val isBooking: Boolean = false,
    val bookingSuccess: Boolean = false,
    val errorMessage: String? = null
)
