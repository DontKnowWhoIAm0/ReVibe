package com.revibe.feature.product_details.data

import com.revibe.feature.product_details.domain.model.ProductDetail

interface ProductDetailsRepository {
    suspend fun getProduct(article: String): ProductDetail
    suspend fun addToFavourites(userId: String, productId: String)
    suspend fun removeFromFavourites(userId: String, productId: String)
    suspend fun isFavourite(userId: String, article: String): Boolean
}