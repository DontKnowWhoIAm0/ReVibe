package com.revibe.feature.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revibe.feature.cart.domain.usecase.BookAllUseCase
import com.revibe.feature.cart.domain.usecase.ObserveCartUseCase
import com.revibe.feature.cart.domain.usecase.RemoveFromCartUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val observeCartUseCase: ObserveCartUseCase,
    private val bookAllUseCase: BookAllUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val userId: String
) : ViewModel() {

    private val _state = MutableStateFlow(CartUiState())
    val state: StateFlow<CartUiState> = _state

    init {
        viewModelScope.launch {
            observeCartUseCase().collect { items ->
                _state.value = _state.value.copy(
                    itemsByBranch = items.groupBy { it.branchAddress }
                )
            }
        }
    }

    fun removeItem(article: String) {
        viewModelScope.launch {
            removeFromCartUseCase(article)
        }
    }

    fun bookAll() {
        _state.value = _state.value.copy(isBooking = true, errorMessage = null)
        viewModelScope.launch {
            try {
                bookAllUseCase(userId)
                _state.value = _state.value.copy(isBooking = false, bookingSuccess = true)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isBooking = false, errorMessage = e.message ?: "Ошибка бронирования"
                )
            }
        }
    }
}
