package com.revibe.feature.product_details.domain.usecase

import com.revibe.feature.product_details.data.ProductDetailsRepository
import com.revibe.feature.product_details.domain.model.ProductDetail
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: ProductDetailsRepository
) {
    suspend operator fun invoke(article: String): ProductDetail =
        repository.getProduct(article)
}