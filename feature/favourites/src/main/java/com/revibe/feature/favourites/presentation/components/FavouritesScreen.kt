package com.revibe.feature.favourites.presentation.components

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
import com.revibe.feature.favourites.domain.model.FavouriteProduct
import com.revibe.feature.favourites.presentation.FavouritesViewModel

@Composable
fun FavouritesScreen(
    viewModel: FavouritesViewModel,
    onProductClick: (FavouriteProduct) -> Unit = {},
    onCartClick: (FavouriteProduct) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val colors = MaterialTheme.colorScheme

    Scaffold(
        containerColor = colors.background,
        topBar = { FavouritesTopBar() }
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
                            color = colors.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Button(onClick = { viewModel.loadFavourites() }) {
                            Text("Повторить")
                        }
                    }
                }

                state.products.isEmpty() -> {
                    Text(
                        text = "Нет избранных товаров",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.onSurfaceVariant
                    )
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
                                isFavourite = true,
                                isInCart = false,
                                onClick = { onProductClick(product) },
                                onFavoriteClick = { viewModel.removeFromFavourites(product.article) },
                                onCartClick = { onCartClick(product) }
                            )
                        }
                    }
                }
            }
        }
    }
}
