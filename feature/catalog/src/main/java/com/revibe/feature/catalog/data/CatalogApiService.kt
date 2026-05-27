package com.revibe.feature.catalog.data

import com.revibe.feature.catalog.data.dto.ProductDto
import retrofit2.Response
import retrofit2.http.GET

interface CatalogApiService {
    @GET("api/products")
    suspend fun getAllProducts(): Response<List<ProductDto>>
}