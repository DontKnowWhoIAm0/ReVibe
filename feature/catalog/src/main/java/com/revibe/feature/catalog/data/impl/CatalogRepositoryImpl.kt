package com.revibe.feature.catalog.data.impl

import com.revibe.feature.catalog.data.CatalogApiService
import com.revibe.feature.catalog.data.CatalogRepository
import com.revibe.feature.catalog.data.dto.ProductDto
import com.revibe.feature.catalog.domain.model.Product
import org.json.JSONObject
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val apiService: CatalogApiService
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

    private fun ProductDto.toDomain() = Product(
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