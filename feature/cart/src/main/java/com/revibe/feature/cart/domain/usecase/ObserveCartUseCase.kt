package com.revibe.feature.cart.domain.usecase

import com.revibe.feature.cart.data.CartRepository
import com.revibe.feature.cart.domain.model.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(): Flow<List<CartItem>> = repository.observeCartItems().map { entities ->
        entities.map {
            CartItem(
                article = it.article,
                name = it.name,
                price = it.price,
                imageUrl = it.imageUrl,
                brand = it.brand,
                size = it.size,
                category = it.category,
                condition = it.condition,
                branchId = it.branchId,
                branchAddress = it.branchAddress
            )
        }
    }
}
