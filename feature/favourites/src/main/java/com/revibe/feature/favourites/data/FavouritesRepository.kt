package com.revibe.feature.favourites.data

import com.revibe.feature.favourites.domain.model.FavouriteProduct
import java.util.UUID

interface FavouritesRepository {
    suspend fun getFavourites(): List<FavouriteProduct>
    suspend fun removeFromFavourites(article: UUID)
}