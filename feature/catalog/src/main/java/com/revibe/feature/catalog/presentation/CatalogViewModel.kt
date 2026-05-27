package com.revibe.feature.catalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revibe.feature.catalog.domain.usecase.GetFavouriteArticlesUseCase
import com.revibe.feature.catalog.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getFavouriteArticlesUseCase: GetFavouriteArticlesUseCase,
    private val userId: String
) : ViewModel() {

    private val _state = MutableStateFlow(CatalogUiState())
    val state: StateFlow<CatalogUiState> = _state

    init {
        loadProducts()
    }

    fun loadProducts() {
        _state.value = _state.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val products = getProductsUseCase()
                val favouriteArticles = try {
                    getFavouriteArticlesUseCase(userId).toSet()
                } catch (e: Exception) {
                    emptySet()
                }

                _state.value = _state.value.copy(isLoading = false, products = products, favouriteArticles = favouriteArticles)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Неизвестная ошибка"
                )
            }
        }
    }
}