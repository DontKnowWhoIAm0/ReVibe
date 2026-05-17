package com.revibe.feature.product_details.data

import com.revibe.feature.product_details.domain.model.ProductDetail

interface ProductDetailsRepository {
    suspend fun getProduct(article: String): ProductDetail
}