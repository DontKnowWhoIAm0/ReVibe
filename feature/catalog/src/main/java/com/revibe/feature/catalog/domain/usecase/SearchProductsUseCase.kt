package com.revibe.feature.catalog.domain.usecase

import com.revibe.feature.catalog.domain.model.Product
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor() {
    operator fun invoke(products: List<Product>, query: String): List<Product> {
        if (query.isBlank()) return products
        val q = query.trim().lowercase()
        return products.filter { product ->
            product.name.lowercase().contains(q)
        }
    }
}
