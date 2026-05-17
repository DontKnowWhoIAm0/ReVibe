package com.revibe.feature.product_details.data

import com.revibe.feature.product_details.data.dto.ProductDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailsApiService {
    @GET("api/products/{id}")
    suspend fun getProduct(@Path("id") article: String): Response<ProductDetailDto>
}