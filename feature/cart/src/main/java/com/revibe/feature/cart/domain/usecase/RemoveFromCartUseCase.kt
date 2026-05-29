package com.revibe.feature.cart.domain.usecase

import com.revibe.feature.cart.data.CartRepository
import javax.inject.Inject

class RemoveFromCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(article: String) = repository.removeFromCart(article)
}
