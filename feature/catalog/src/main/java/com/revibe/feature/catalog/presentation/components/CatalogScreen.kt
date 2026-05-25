package com.revibe.feature.catalog.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.revibe.core.ui.components.ProductCard
import com.revibe.feature.catalog.domain.model.Product
import com.revibe.feature.catalog.presentation.CatalogViewModel

@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel,
    onProductClick: (Product) -> Unit = {},
    onFavoriteClick: (Product) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onFilterClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Scaffold(
        containerColor = colors.background,
        topBar = {
            CatalogTopBar(
                onSearchClick = onSearchClick,
                onFilterClick = onFilterClick
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.errorMessage != null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = state.errorMessage!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Button(onClick = { viewModel.loadProducts() }) {
                            Text("Повторить")
                        }
                    }
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.products, key = { it.article.toString() }) { product ->
                            ProductCard(
                                name = product.name,
                                price = "${product.price} ₽",
                                imageUrl = product.imageUrl,
                                isFavourite = false,
                                onClick = { onProductClick(product) },
                                onFavoriteClick = { onFavoriteClick(product) },
                                onCartClick = { /* TODO */ }
                            )
                        }
                    }
                }
            }
        }
    }
}