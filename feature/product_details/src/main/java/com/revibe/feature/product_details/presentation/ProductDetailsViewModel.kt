package com.revibe.feature.product_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revibe.core.db.dao.CartDao
import com.revibe.core.db.entity.CartItemEntity
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
    @IsFavourite private val isFavourite: Boolean,
    private val cartDao: CartDao
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
                val inCart = cartDao.isInCart(article)
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
                    isFavourite = actualIsFavourite,
                    isInCart = inCart
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

    fun toggleCart() {
        val current = _state.value.isInCart
        _state.value = _state.value.copy(isInCart = !current)

        viewModelScope.launch {
            try {

                if (current) {

                    cartDao.getAll().find { it.article == article }?.let { cartDao.delete(it) }

                } else {

                    val product = getProductUseCase(article)

                    cartDao.insert(
                        CartItemEntity(
                            article = product.article,
                            name = product.name,
                            price = product.price,
                            imageUrl = product.imageUrl,
                            brand = product.brand,
                            size = product.size,
                            category = product.category ?: "",
                            condition = product.condition,
                            branchId = "",
                            branchAddress = product.location
                        )
                    )
                }

            } catch (e: Exception) {
                _state.value =
                    _state.value.copy(isInCart = current)
            }
        }
    }
}