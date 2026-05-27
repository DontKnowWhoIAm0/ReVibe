package com.revibe.feature.favourites.data.impl

import com.revibe.feature.favourites.data.FavouritesRepository
import com.revibe.feature.favourites.domain.model.FavouriteProduct
import com.revibe.core.network.favourites.FavouriteItemDto
import com.revibe.core.network.favourites.FavouritesApiService
import com.revibe.core.network.util.parseErrorMessage
import java.util.UUID
import javax.inject.Inject

class FavouritesRepositoryImpl @Inject constructor(
    private val apiService: FavouritesApiService
) : FavouritesRepository {

    override suspend fun getFavourites(userId: String): List<FavouriteProduct> {
        val response = apiService.getFavourites(userId)
        if (response.isSuccessful) return response.body()!!.map { it.toDomain() }
        throw Exception(parseErrorMessage(response.errorBody()?.string(), response.code()))
    }

    override suspend fun removeFromFavourites(userId: String, article: UUID) {
        val response = apiService.removeFromFavourites(article.toString(), userId)
        if (!response.isSuccessful)
            throw Exception(parseErrorMessage(response.errorBody()?.string(), response.code()))
    }

    override suspend fun isFavourite(userId: String, article: UUID): Boolean {
        val response = apiService.isFavourite(userId, article.toString())
        if (response.isSuccessful) return response.body() ?: false
        throw Exception("Ошибка ${response.code()}")
    }

    private fun FavouriteItemDto.toDomain() = FavouriteProduct(
        article = article, name = name, price = price, imageUrl = imageUrl, gender = gender,
        color = color, brand = brand, size = size
    )
}