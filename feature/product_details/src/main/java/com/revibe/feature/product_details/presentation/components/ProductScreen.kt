    package com.revibe.feature.product_details.presentation.components

    import androidx.compose.foundation.*
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.shape.*
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.ArrowBack
    import androidx.compose.material3.*
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.ui.*
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.*
    import coil.compose.AsyncImage
    import com.revibe.feature.product_details.presentation.ProductDetailsViewModel
    import com.revibe.core.ui.R as CoreR

    @Composable
    fun ProductScreen(
        viewModel: ProductDetailsViewModel,
        onBackClick: () -> Unit,
        onCreateOutfitClick: () -> Unit,
        onViewOutfitClick: () -> Unit,
        onFavoriteClick: () -> Unit,
        onAddToCartClick: () -> Unit = {}
    ) {
        val state by viewModel.state.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(
                                bottomStart = 32.dp,
                                bottomEnd = 32.dp
                            )
                        )
                        .zIndex(1f)
                ) {
                    AsyncImage(
                        model = state.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp)
                    )

                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(40.dp)
                            .background(
                                MaterialTheme.colorScheme.background,
                                RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = state.title,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = state.brand,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = state.price,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(16.dp))

                        InfoRow("Артикул:", state.article)
                        InfoRow("Размер:", state.size)
                        InfoRow("Состояние:", state.condition)
                        InfoRow("Адрес филиала:", state.location)
                        InfoRow("Пол:", state.gender)

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = onCreateOutfitClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text("Создать аутфит")
                        }

                        Spacer(Modifier.height(12.dp))

                        OutlinedButton(
                            onClick = onViewOutfitClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text("Посмотреть аутфит")
                        }
                    }
                }
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 300.dp - 24.dp)
                    .zIndex(2f),
                contentAlignment = Alignment.TopEnd
            ) {
                Row(
                    modifier = Modifier
                        .padding(end = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconActionButton(
                        icon = painterResource(CoreR.drawable.add_to_favourite),
                        onClick = onFavoriteClick
                    )

                    IconActionButton(
                        icon = painterResource(CoreR.drawable.add_to_cart),
                        onClick = onAddToCartClick
                    )
                }
            }

        }
    }

