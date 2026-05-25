package com.revibe.feature.favourites.data.impl

import com.revibe.feature.favourites.data.FavouritesRepository
import com.revibe.feature.favourites.data.dto.FavouriteDto
import com.revibe.feature.favourites.domain.model.FavouriteProduct
import com.revibe.feature.favourites.data.FavouritesApiService
import org.json.JSONObject
import java.util.UUID
import javax.inject.Inject

class FavouritesRepositoryImpl @Inject constructor(
    private val apiService: FavouritesApiService
) : FavouritesRepository {

    override suspend fun getFavourites(): List<FavouriteProduct> {
        val response = apiService.getFavourites()

        if (response.isSuccessful) {
            return response.body()!!.map { it.toDomain() }
        }

        val errorBody = response.errorBody()?.string()
        val errorMessage = try {
            JSONObject(errorBody ?: "").getString("error")
        } catch (e: Exception) {
            "Ошибка ${response.code()}"
        }

        throw Exception(errorMessage)
    }

    override suspend fun removeFromFavourites(article: UUID) {
        val response = apiService.removeFromFavourites(article)

        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorBody ?: "").getString("error")
            } catch (e: Exception) {
                "Ошибка ${response.code()}"
            }
            throw Exception(errorMessage)
        }
    }

    private fun FavouriteDto.toDomain() = FavouriteProduct(
        article  = article,
        name     = name,
        price    = price,
        imageUrl = imageUrl,
        gender   = gender,
        color    = color,
        brand    = brand,
        size     = size
    )
}