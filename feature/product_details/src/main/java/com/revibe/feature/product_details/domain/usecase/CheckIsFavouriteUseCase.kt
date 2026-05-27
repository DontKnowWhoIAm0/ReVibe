package com.revibe.feature.product_details.domain.usecase

import com.revibe.feature.product_details.data.ProductDetailsRepository
import javax.inject.Inject

class CheckIsFavouriteUseCase @Inject constructor(
    private val repository: ProductDetailsRepository
) {
    suspend operator fun invoke(userId: String, article: String): Boolean =
        repository.isFavourite(userId, article)
}