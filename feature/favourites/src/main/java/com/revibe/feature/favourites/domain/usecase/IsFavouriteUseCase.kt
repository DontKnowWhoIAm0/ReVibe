package com.revibe.feature.favourites.domain.usecase

import com.revibe.feature.favourites.data.FavouritesRepository
import java.util.UUID
import javax.inject.Inject

class IsFavouriteUseCase @Inject constructor(
    private val repository: FavouritesRepository
) {
    suspend operator fun invoke(userId: String, article: UUID): Boolean = repository.isFavourite(userId, article)
}