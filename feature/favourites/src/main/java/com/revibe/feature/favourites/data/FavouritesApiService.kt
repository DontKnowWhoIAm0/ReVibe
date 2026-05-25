package com.revibe.feature.favourites.data

import com.revibe.feature.favourites.data.dto.FavouriteDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.UUID

interface FavouritesApiService {

    @GET("api/favourites")
    suspend fun getFavourites(): Response<List<FavouriteDto>>

    @DELETE("api/favourites/{article}")
    suspend fun removeFromFavourites(@Path("article") article: UUID): Response<Unit>
}