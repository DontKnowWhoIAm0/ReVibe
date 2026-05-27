package com.revibe.core.network.favourites

import retrofit2.Response
import retrofit2.http.*

interface FavouritesApiService {

    @GET("api/favorites")
    suspend fun getFavourites(
        @Query("userId") userId: String
    ): Response<List<FavouriteItemDto>>

    @POST("api/favorites")
    suspend fun addToFavourites(
        @Query("userId") userId: String,
        @Query("productId") productId: String
    ): Response<Unit>

    @DELETE("api/favorites/{productId}")
    suspend fun removeFromFavourites(
        @Path("productId") productId: String,
        @Query("userId") userId: String
    ): Response<Unit>

    @GET("api/favorites/check")
    suspend fun isFavourite(
        @Query("userId") userId: String,
        @Query("productId") productId: String
    ): Response<Boolean>
}