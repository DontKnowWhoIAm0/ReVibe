package com.revibe.feature.catalog.domain.usecase

import com.revibe.feature.catalog.data.CatalogRepository
import com.revibe.feature.catalog.domain.model.Product
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: CatalogRepository
) {
    suspend operator fun invoke(): List<Product> = repository.getProducts()
}