package com.revibe.feature.catalog.presentation

import com.revibe.feature.catalog.domain.model.Product

data class CatalogUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)