package com.revibe.feature.cart.domain.usecase

import com.revibe.feature.cart.data.CartRepository
import javax.inject.Inject

class BookAllUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(userId: String) = repository.bookAll(userId)
}
