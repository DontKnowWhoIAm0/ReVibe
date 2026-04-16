package com.revibe.feature.product_details.domain.usecase

import com.revibe.feature.product_details.domain.repository.ProductRepository

class AddToFavoriteUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(article: String) {
        repository.addToFavorite(article)
    }
}
