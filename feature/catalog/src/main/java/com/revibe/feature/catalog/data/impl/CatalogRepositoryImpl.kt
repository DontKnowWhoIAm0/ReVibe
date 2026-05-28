package com.revibe.feature.catalog.data.impl

import com.revibe.core.network.favourites.FavouriteItemDto
import com.revibe.core.network.favourites.FavouritesApiService
import com.revibe.core.network.util.parseErrorMessage
import com.revibe.feature.catalog.data.CatalogApiService
import com.revibe.feature.catalog.data.CatalogRepository
import com.revibe.feature.catalog.data.dto.ProductDto
import com.revibe.feature.catalog.domain.model.Product
import org.json.JSONObject
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val apiService: CatalogApiService,
    private val favouritesApiService: FavouritesApiService
) : CatalogRepository {

    override suspend fun getProducts(): List<Product> {
        val response = apiService.getAllProducts()

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

    override suspend fun getFavourites(userId: String): List<FavouriteItemDto> {
        val response = favouritesApiService.getFavourites(userId)
        if (response.isSuccessful) return response.body() ?: emptyList()
        throw Exception(parseErrorMessage(response.errorBody()?.string(), response.code()))
    }

    override suspend fun addFavourite(userId: String, article: String) {
        val response = favouritesApiService.addToFavourites(userId, article)
        if (!response.isSuccessful)
            throw Exception(parseErrorMessage(response.errorBody()?.string(), response.code()))
    }

    override suspend fun removeFavourite(userId: String, article: String) {
        val response = favouritesApiService.removeFromFavourites(article, userId)
        if (!response.isSuccessful)
            throw Exception(parseErrorMessage(response.errorBody()?.string(), response.code()))
    }

    private fun ProductDto.toDomain() = Product(
        article = article,
        name = name,
        price = price,
        imageUrl = imageUrl,
        gender = gender,
        color = color,
        brand = brand,
        size = size,
        condition = condition,
        category = category
    )
}