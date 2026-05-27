package com.revibe.feature.catalog.data

import com.revibe.core.network.favourites.FavouriteItemDto
import com.revibe.feature.catalog.domain.model.Product

interface CatalogRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getFavourites(userId: String): List<FavouriteItemDto>

    suspend fun addFavourite(userId: String, article: String)

    suspend fun removeFavourite(userId: String, article: String)
}