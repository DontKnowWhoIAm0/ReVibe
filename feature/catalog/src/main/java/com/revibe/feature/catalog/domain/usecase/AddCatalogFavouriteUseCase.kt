package com.revibe.feature.catalog.domain.usecase

import com.revibe.feature.catalog.data.CatalogRepository
import javax.inject.Inject

class AddCatalogFavouriteUseCase @Inject constructor(
    private val repository: CatalogRepository
) {
    suspend operator fun invoke(userId: String, article: String) =
        repository.addFavourite(userId, article)
}