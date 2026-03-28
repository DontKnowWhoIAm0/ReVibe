package com.revibe.feature.catalog.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.revibe.core.ui.components.ProductCard
import com.revibe.core.R as CoreR

@Composable
fun CatalogScreen(
    products: List<String>,
    onProductClick: (String) -> Unit = {},
    onFavoriteClick: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onFilterClick: () -> Unit = {}
) {
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { price ->
                ProductCard(
                    price = price,
                    onClick = { onProductClick(price) },
                    onFavoriteClick = { onFavoriteClick(price) }
                )
            }
        }
    }
}

@Composable
private fun CatalogTopBar(
    onSearchClick: () -> Unit = {},
    onFilterClick: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = CoreR.drawable.small_logo),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
        )


        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onSearchClick) {
            Icon(
                painter = painterResource(id = CoreR.drawable.search),
                contentDescription = "Search",
                tint = colors.primary
            )
        }

        IconButton(onClick = onFilterClick) {
            Icon(
                painter = painterResource(id = CoreR.drawable.filters),
                contentDescription = "Filter",
                tint = colors.primary
            )
        }
    }
}