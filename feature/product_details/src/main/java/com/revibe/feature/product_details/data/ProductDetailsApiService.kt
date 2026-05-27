package com.revibe.feature.product_details.data

import com.revibe.feature.product_details.data.dto.ProductDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductDetailsApiService {
    @GET("api/products/{id}")
    suspend fun getProduct(@Path("id") article: String): Response<ProductDetailDto>

    @POST("api/favorites")
    suspend fun addToFavourites(@Query("userId") userId: String, @Query("productId") productId: String): Response<Unit>

    @DELETE("api/favorites/{productId}")
    suspend fun removeFromFavourites(@Path("productId") productId: String, @Query("userId") userId: String): Response<Unit>

    @GET("api/favorites/check")
    suspend fun isFavourite(@Query("userId") userId: String, @Query("productId") productId: String): Response<Boolean>
}