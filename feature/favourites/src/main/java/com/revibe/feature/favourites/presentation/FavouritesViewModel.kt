package com.revibe.feature.favourites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revibe.feature.favourites.domain.usecase.GetFavouritesUseCase
import com.revibe.feature.favourites.domain.usecase.RemoveFromFavouritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavouritesUiState())
    val state: StateFlow<FavouritesUiState> = _state

    init {
        loadFavourites()
    }

    fun loadFavourites() {
        _state.value = _state.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val products = getFavouritesUseCase()
                _state.value = _state.value.copy(isLoading = false, products = products)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Неизвестная ошибка"
                )
            }
        }
    }

    fun removeFromFavourites(article: UUID) {
        viewModelScope.launch {
            try {
                removeFromFavouritesUseCase(article)
                _state.value = _state.value.copy(
                    products = _state.value.products.filter { it.article != article }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = e.message ?: "Не удалось удалить из избранного"
                )
            }
        }
    }
}