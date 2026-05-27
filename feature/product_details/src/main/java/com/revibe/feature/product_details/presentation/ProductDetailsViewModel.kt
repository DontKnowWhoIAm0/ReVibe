package com.revibe.feature.product_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revibe.feature.product_details.di.ArticleId
import com.revibe.feature.product_details.di.IsFavourite
import com.revibe.feature.product_details.di.UserId
import com.revibe.feature.product_details.domain.usecase.AddToFavouritesUseCase
import com.revibe.feature.product_details.domain.usecase.CheckIsFavouriteUseCase
import com.revibe.feature.product_details.domain.usecase.GetProductUseCase
import com.revibe.feature.product_details.domain.usecase.RemoveFromFavouritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val addToFavouritesUseCase: AddToFavouritesUseCase,
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
    private val checkIsFavouriteUseCase: CheckIsFavouriteUseCase,
    @ArticleId private val article: String,
    @UserId private val userId: String,
    @IsFavourite private val isFavourite: Boolean
) : ViewModel() {

    private val _state = MutableStateFlow(ProductUiState())
    val state: StateFlow<ProductUiState> = _state

    init {
        loadProduct()
    }

    fun loadProduct() {
        _state.value = _state.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            try {
                val product = getProductUseCase(article)
                val actualIsFavourite = checkIsFavouriteUseCase(userId, article)
                _state.value = _state.value.copy(
                    isLoading = false,
                    article = product.article,
                    title = product.name,
                    brand = product.brand,
                    price = "${product.price} ₽",
                    size = product.size,
                    condition = product.condition,
                    gender = product.gender,
                    location = product.location,
                    imageUrl = product.imageUrl,
                    isFavourite = actualIsFavourite
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Неизвестная ошибка"
                )
            }
        }
    }

    fun toggleFavourite() {
        val current = _state.value.isFavourite
        _state.value = _state.value.copy(isFavourite = !current, favouriteError = null)

        viewModelScope.launch {
            try {
                if (current) {
                    removeFromFavouritesUseCase(userId, article)
                } else {
                    addToFavouritesUseCase(userId, article)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isFavourite   = current,
                    favouriteError = e.message ?: "Не удалось обновить избранное"
                )
            }
        }
    }
}