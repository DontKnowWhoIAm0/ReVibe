package com.revibe.feature.catalog.data

import com.revibe.feature.catalog.data.dto.FavouriteArticleDto
import com.revibe.feature.catalog.domain.model.Product

interface CatalogRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getFavourites(userId: String): List<FavouriteArticleDto>
}