package com.revibe.feature.product_details.data.impl

import com.revibe.feature.product_details.data.ProductDetailsApiService
import com.revibe.feature.product_details.data.ProductDetailsRepository
import com.revibe.feature.product_details.data.dto.ProductDetailDto
import com.revibe.feature.product_details.domain.model.ProductDetail
import org.json.JSONObject
import javax.inject.Inject

class ProductDetailsRepositoryImpl @Inject constructor(
    private val apiService: ProductDetailsApiService
) : ProductDetailsRepository {

    override suspend fun getProduct(article: String): ProductDetail {
        val response = apiService.getProduct(article)

        if (response.isSuccessful) {
            return response.body()!!.toDomain()
        }

        val errorBody = response.errorBody()?.string()
        val errorMessage = try {
            JSONObject(errorBody ?: "").getString("error")
        } catch (e: Exception) {
            "Ошибка ${response.code()}"
        }

        throw Exception(errorMessage)
    }

    private fun ProductDetailDto.toDomain() = ProductDetail(
        article   = article.toString(),
        name      = name,
        description = description,
        price     = price,
        category  = category,
        gender    = gender ?: "",
        color     = color ?: "",
        brand     = brand ?: "",
        size      = size ?: "",
        condition = condition ?: "",
        imageUrl  = imageUrl,
        location  = branch?.address ?: ""
    )
}