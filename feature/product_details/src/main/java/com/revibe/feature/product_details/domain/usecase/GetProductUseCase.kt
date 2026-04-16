package com.revibe.feature.product_details.domain.usecase

import com.revibe.feature.product_details.domain.model.ProductDomainModel
import com.revibe.feature.product_details.domain.repository.ProductRepository


class GetProductUseCase(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(productId: Long): ProductDomainModel {
        return repository.getProduct(productId)
    }
}
