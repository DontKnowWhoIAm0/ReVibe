package com.revibe.feature.catalog.domain.usecase

import com.revibe.feature.catalog.data.CatalogRepository
import java.util.UUID
import javax.inject.Inject

class GetFavouriteArticlesUseCase @Inject constructor(
    private val repository: CatalogRepository
) {
    suspend operator fun invoke(userId: String): Set<UUID> = repository.getFavourites(userId).map { it.article }.toSet()
}