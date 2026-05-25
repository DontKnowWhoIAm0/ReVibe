package com.revibe.feature.favourites.domain.usecase

import com.revibe.feature.favourites.data.FavouritesRepository
import java.util.UUID
import javax.inject.Inject

class RemoveFromFavouritesUseCase @Inject constructor(
    private val repository: FavouritesRepository
) {
    suspend operator fun invoke(article: UUID) = repository.removeFromFavourites(article)
}