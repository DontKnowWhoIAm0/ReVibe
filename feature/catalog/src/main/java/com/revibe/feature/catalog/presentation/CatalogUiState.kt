package com.revibe.feature.catalog.presentation

import com.revibe.feature.catalog.domain.model.Product
import java.util.UUID

data class CatalogUiState(
    val allProducts: List<Product> = emptyList(),
    val products: List<Product> = emptyList(),
    val favouriteArticles: Set<UUID> = emptySet(),
    val filters: FiltersState = FiltersState(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
