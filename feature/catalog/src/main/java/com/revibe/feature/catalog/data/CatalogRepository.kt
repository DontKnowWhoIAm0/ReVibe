package com.revibe.feature.catalog.data

import com.revibe.feature.catalog.domain.model.Product

interface CatalogRepository {
    suspend fun getProducts(): List<Product>
}