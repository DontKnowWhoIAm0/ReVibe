package com.revibe.feature.catalog.domain.usecase

import com.revibe.feature.catalog.data.CatalogRepository
import javax.inject.Inject

class RemoveCatalogFavouriteUseCase @Inject constructor(
    private val repository: CatalogRepository
) {
    suspend operator fun invoke(userId: String, article: String) =
        repository.removeFavourite(userId, article)
}