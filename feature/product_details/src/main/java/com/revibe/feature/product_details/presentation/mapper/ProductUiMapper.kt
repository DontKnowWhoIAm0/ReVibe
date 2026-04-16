package com.revibe.feature.product_details.presentation.mapper

import com.revibe.feature.product_details.domain.model.ProductDomainModel
import com.revibe.feature.product_details.presentation.ProductUiState

fun ProductDomainModel.toProductState(): ProductUiState =
    ProductUiState(
        title = title,
        brand = brand,
        price = priceFormatted,
        article = article,
        size = size,
        condition = condition,
        location = location,
        gender = gender,
        imageUrl = imageUrl
    )