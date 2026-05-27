package com.revibe.feature.favourites.data

import com.revibe.feature.favourites.data.dto.FavouriteDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FavouritesApiService {

    @GET("api/favorites")
    suspend fun getFavourites(@Query("userId") userId: String): Response<List<FavouriteDto>>

    @DELETE("api/favorites/{article}")
    suspend fun removeFromFavourites(@Path("article") article: String, @Query("userId") userId: String): Response<Unit>

    @GET("api/favorites/check")
    suspend fun isFavourite(@Query("userId") userId: String, @Query("productId") productId: String): Response<Boolean>
}