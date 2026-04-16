package com.revibe.feature.product_details.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import androidx.lifecycle.viewModelScope
import com.revibe.feature.product_details.domain.usecase.AddToCartUseCase
import com.revibe.feature.product_details.domain.usecase.AddToFavoriteUseCase
import com.revibe.feature.product_details.domain.usecase.GetProductUseCase
import com.revibe.feature.product_details.presentation.mapper.toProductState
import kotlinx.coroutines.launch

class ProductViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ProductUiState?>(null)
    val state: StateFlow<ProductUiState?> = _state.asStateFlow()

    private val _effect = Channel<ProductEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun loadProduct(productId: Long) {
        viewModelScope.launch {
            runCatching {
                getProductUseCase(productId)
            }.onSuccess { product ->
                _state.value = product.toProductState()
            }.onFailure {
                _effect.send(
                    ProductEffect.ShowMessage(
                        it.message ?: "Ошибка загрузки товара"
                    )
                )
            }
        }
    }

    fun onEvent(event: ProductEvent) {
        when (event) {
            ProductEvent.OnBackClick ->
                emitEffect(ProductEffect.NavigateBack)

            ProductEvent.OnCreateOutfitClick ->
                emitEffect(ProductEffect.NavigateToCreateOutfit)

            ProductEvent.OnViewOutfitClick ->
                emitEffect(ProductEffect.NavigateToViewOutfit)

            ProductEvent.OnFavoriteClick ->
                addToFavorite()

            ProductEvent.OnAddToCartClick ->
                addToCart()
        }
    }

    private fun addToFavorite() {
        val product = _state.value ?: return
        viewModelScope.launch {
            addToFavoriteUseCase(product.article)
            emitEffect(ProductEffect.ShowMessage("Добавлено в избранное"))
        }
    }

    private fun addToCart() {
        val product = _state.value ?: return
        viewModelScope.launch {
            addToCartUseCase(product.article)
            emitEffect(ProductEffect.ShowMessage("Добавлено в корзину"))
        }
    }

    private fun emitEffect(effect: ProductEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}