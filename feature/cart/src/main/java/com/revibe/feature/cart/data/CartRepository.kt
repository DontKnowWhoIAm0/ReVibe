package com.revibe.feature.cart.data

import com.revibe.core.db.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun observeCartItems(): Flow<List<CartItemEntity>>
    suspend fun addToCart(item: CartItemEntity)
    suspend fun removeFromCart(article: String)
    suspend fun isInCart(article: String): Boolean
    suspend fun clearCart()
    suspend fun bookAll(userId: String)
}
