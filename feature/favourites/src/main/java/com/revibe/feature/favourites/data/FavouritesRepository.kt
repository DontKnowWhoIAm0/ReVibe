package com.revibe.feature.favourites.data

import com.revibe.feature.favourites.domain.model.FavouriteProduct
import java.util.UUID

interface FavouritesRepository {
    suspend fun getFavourites(userId: String): List<FavouriteProduct>
    suspend fun removeFromFavourites(userId: String, article: UUID)
    suspend fun isFavourite(userId: String, article: UUID): Boolean
}