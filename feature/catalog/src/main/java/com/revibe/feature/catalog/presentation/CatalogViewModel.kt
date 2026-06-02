package com.revibe.feature.catalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revibe.feature.catalog.domain.model.Product
import com.revibe.feature.catalog.domain.usecase.AddCatalogFavouriteUseCase
import com.revibe.feature.catalog.domain.usecase.FilterProductsUseCase
import com.revibe.feature.catalog.domain.usecase.GetFavouriteArticlesUseCase
import com.revibe.feature.catalog.domain.usecase.GetProductsUseCase
import com.revibe.feature.catalog.domain.usecase.RemoveCatalogFavouriteUseCase
import com.revibe.feature.catalog.domain.usecase.SearchProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.revibe.core.db.dao.CartDao
import com.revibe.core.db.entity.CartItemEntity

class CatalogViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getFavouriteArticlesUseCase: GetFavouriteArticlesUseCase,
    private val addFavouriteUseCase: AddCatalogFavouriteUseCase,
    private val removeFavouriteUseCase: RemoveCatalogFavouriteUseCase,
    private val filterProductsUseCase: FilterProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase,
    private val userId: String,
    private val cartDao: CartDao
) : ViewModel() {

    private val _state = MutableStateFlow(CatalogUiState())
    val state: StateFlow<CatalogUiState> = _state

    init {
        loadProducts()

        viewModelScope.launch {
            cartDao.observeAll().collect { items ->
                _state.value = _state.value.copy(
                    cartArticles = items.map {
                        java.util.UUID.fromString(it.article)
                    }.toSet()
                )
            }
        }
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

                val filtered = filterProductsUseCase(products, _state.value.filters)
                _state.value = _state.value.copy(
                    isLoading = false,
                    allProducts = products,
                    products = filtered,
                    favouriteArticles = favouriteArticles
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Неизвестная ошибка"
                )
            }
        }
    }

    fun applyFilters(filters: FiltersState) {
        val filtered = filterProductsUseCase(_state.value.allProducts, filters)
        val searched = searchProductsUseCase(filtered, _state.value.searchQuery)
        _state.value = _state.value.copy(filters = filters, products = searched)
    }

    fun resetFilters() {
        _state.value = _state.value.copy(
            filters = FiltersState(),
            products = _state.value.allProducts
        )
    }

    fun toggleFavourite(product: Product) {
        val isFav = product.article in _state.value.favouriteArticles
        _state.value = _state.value.copy(
            favouriteArticles = if (isFav)
                _state.value.favouriteArticles - product.article
            else
                _state.value.favouriteArticles + product.article
        )
        viewModelScope.launch {
            try {
                if (isFav) {
                    removeFavouriteUseCase(userId, product.article.toString())
                } else {
                    addFavouriteUseCase(userId, product.article.toString())
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    favouriteArticles = if (isFav)
                        _state.value.favouriteArticles + product.article
                    else
                        _state.value.favouriteArticles - product.article
                )
            }
        }
    }


    fun applySearch(query: String) {
        val afterFilter = filterProductsUseCase(_state.value.allProducts, _state.value.filters)
        val afterSearch = searchProductsUseCase(afterFilter, query)
        _state.value = _state.value.copy(searchQuery = query, products = afterSearch)
    }

    fun toggleCart(product: Product) {
        viewModelScope.launch {

            val isInCart =
                product.article in _state.value.cartArticles

            if (isInCart) {

                cartDao.getAll()
                    .find {
                        it.article == product.article.toString()
                    }
                    ?.let {
                        cartDao.delete(it)
                    }

            } else {

                cartDao.insert(
                    CartItemEntity(
                        article = product.article.toString(),
                        name = product.name,
                        price = product.price,
                        imageUrl = product.imageUrl,
                        brand = product.brand,
                        size = product.size,
                        category = product.category,
                        condition = product.condition,
                        branchId = product.branchId,
                        branchAddress = product.branchAddress
                    )
                )
            }
        }
    }
}
