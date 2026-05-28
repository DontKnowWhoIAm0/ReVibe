package com.revibe.feature.catalog.domain.usecase

import com.revibe.feature.catalog.domain.model.Product
import com.revibe.feature.catalog.presentation.FiltersState
import javax.inject.Inject

class FilterProductsUseCase @Inject constructor() {
    operator fun invoke(products: List<Product>, filters: FiltersState): List<Product> {
        return products.filter { product ->
            (filters.selectedGenders.isEmpty() || product.gender in filters.selectedGenders) &&
                    (filters.selectedSizes.isEmpty() || product.size in filters.selectedSizes) &&
                    (filters.selectedColors.isEmpty() || product.color in filters.selectedColors) &&
                    (filters.selectedBrands.isEmpty() || product.brand in filters.selectedBrands) &&
                    (filters.selectedConditions.isEmpty() || product.condition in filters.selectedConditions) &&
                    (filters.selectedCategories.isEmpty() || product.category in filters.selectedCategories) &&
                    (product.price in filters.minPrice..filters.maxPrice)
        }
    }
}