package com.revibe.feature.catalog.data

import com.revibe.feature.catalog.data.dto.FavouriteArticleDto
import com.revibe.feature.catalog.data.dto.ProductDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CatalogApiService {
    @GET("api/products")
    suspend fun getAllProducts(): Response<List<ProductDto>>

    @GET("api/favorites")
    suspend fun getFavourites(@Query("userId") userId: String): Response<List<FavouriteArticleDto>>

    @POST("api/favorites")
    suspend fun addToFavourites(@Query("userId") userId: String, @Query("productId") productId: String): Response<Unit>

    @DELETE("api/favorites/{productId}")
    suspend fun removeFromFavourites(@Path("productId") productId: String, @Query("userId") userId: String): Response<Unit>
}