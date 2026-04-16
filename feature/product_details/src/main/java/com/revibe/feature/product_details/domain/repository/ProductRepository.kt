package com.revibe.feature.product_details.domain.repository

import com.revibe.feature.product_details.domain.model.ProductDomainModel


interface ProductRepository {

    suspend fun getProduct(productId: Long): ProductDomainModel

    suspend fun addToFavorite(article: String)

    suspend fun addToCart(article: String)
}
