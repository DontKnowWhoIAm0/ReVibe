package com.revibe.feature.product_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revibe.feature.product_details.domain.usecase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val article: String
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
                _state.value = _state.value.copy(
                    isLoading  = false,
                    article    = product.article,
                    title      = product.name,
                    brand      = product.brand,
                    price      = "${product.price} ₽",
                    size       = product.size,
                    condition  = product.condition,
                    gender     = product.gender,
                    location   = product.location,
                    imageUrl   = product.imageUrl
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading    = false,
                    errorMessage = e.message ?: "Неизвестная ошибка"
                )
            }
        }
    }
}