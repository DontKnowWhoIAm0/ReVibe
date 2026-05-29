package com.revibe.feature.cart.data.impl

import com.revibe.core.db.dao.CartDao
import com.revibe.core.db.entity.CartItemEntity
import com.revibe.feature.cart.data.CartApiService
import com.revibe.feature.cart.data.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val apiService: CartApiService
) : CartRepository {

    override fun observeCartItems(): Flow<List<CartItemEntity>> =
        cartDao.observeAll()

    override suspend fun addToCart(item: CartItemEntity) =
        cartDao.insert(item)

    override suspend fun removeFromCart(article: String) {
        val all = cartDao.getAll()
        all.find { it.article == article }?.let { cartDao.delete(it) }
    }

    override suspend fun isInCart(article: String): Boolean = cartDao.isInCart(article)

    override suspend fun clearCart() = cartDao.clearAll()

    override suspend fun bookAll(userId: String) {
        val items = cartDao.getAll()
        items.forEach { item ->
            val response = apiService.createBooking(
                userId = userId,
                productId = item.article,
                branchId = item.branchId
            )
            if (!response.isSuccessful) {
                throw Exception("Ошибка бронирования товара ${item.name}: ${response.code()}")
            }
        }
        cartDao.clearAll()
    }
}
