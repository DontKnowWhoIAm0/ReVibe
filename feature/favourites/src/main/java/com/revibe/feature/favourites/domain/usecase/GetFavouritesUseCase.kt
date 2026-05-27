package com.revibe.feature.favourites.domain.usecase

import com.revibe.feature.favourites.data.FavouritesRepository
import com.revibe.feature.favourites.domain.model.FavouriteProduct
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val repository: FavouritesRepository
) {
    suspend operator fun invoke(userId: String): List<FavouriteProduct> = repository.getFavourites(userId)
}