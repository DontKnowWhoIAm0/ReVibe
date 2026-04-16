package com.revibe.feature.filters

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.revibe.core.R as CoreR

@Composable
fun FiltersScreen(
    onBackClick: () -> Unit = {},
    onShowAllClick: () -> Unit = {},
    onApplyClick: () -> Unit = {},
    onResetClick: () -> Unit = {}
) {
    var priceRange by remember {
        mutableStateOf<ClosedFloatingPointRange<Float>>(1382f..7684f)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            FiltersTopBar(onBackClick)
        },
        bottomBar = {
            FiltersBottomBar(onApplyClick, onResetClick)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {

            FilterSection(
                title = stringResource(R.string.filter_category),
                action = stringResource(R.string.filter_show_all),
                onActionClick = onShowAllClick
            )

            FilterSection(title = stringResource(R.string.filter_gender)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(stringResource(R.string.gender_unisex), true)
                    FilterChip(stringResource(R.string.gender_female))
                    FilterChip(stringResource(R.string.gender_male))
                }
            }

            FilterSection(title = stringResource(R.string.filter_size)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("XS", "S", "M", "L", "XL", "XXL").forEach {
                        FilterChip(it, it == "M" || it == "L")
                    }
                }
            }

            FilterSection(title = stringResource(R.string.filter_color)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ColorChip(stringResource(R.string.color_white))
                    ColorChip(stringResource(R.string.color_black), true)
                    ColorChip(stringResource(R.string.color_blue), true)
                    ColorChip(stringResource(R.string.color_gray), true)
                }
            }

            FilterSection(title = stringResource(R.string.filter_brand)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(stringResource(R.string.brand_1))
                    FilterChip(stringResource(R.string.brand_2))
                    FilterChip(stringResource(R.string.brand_3), true)
                    FilterChip(stringResource(R.string.brand_4))
                }
            }

            FilterSection(title = stringResource(R.string.filter_condition)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(stringResource(R.string.condition_new), true)
                    FilterChip(stringResource(R.string.condition_good))
                }
            }

            FilterSection(title = stringResource(R.string.filter_price)) {
                Text(
                    text = stringResource(
                        R.string.price_range,
                        priceRange.start.toInt(),
                        priceRange.endInclusive.toInt()
                    ),
                    style = MaterialTheme.typography.bodySmall
                )


                RangeSlider(
                    value = priceRange,
                    onValueChange = { priceRange = it },
                    valueRange = 1000f..10000f
                )
            }
        }
    }
}

@Composable
private fun FiltersTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(CoreR.drawable.arrow_back),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = stringResource(R.string.filters_title),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun FiltersBottomBar(
    onApplyClick: () -> Unit,
    onResetClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onApplyClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(50)
        ) {
            Text(stringResource(R.string.apply))
        }

        OutlinedButton(
            onClick = onResetClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(stringResource(R.string.reset))
        }
    }
}


@Composable
private fun FilterSection(
    title: String,
    action: String? = null,
    onActionClick: (() -> Unit)? = null,
    content: @Composable () -> Unit = {}
) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(title, style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.weight(1f))

            action?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onActionClick?.invoke() }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}

@Composable
private fun FilterChip(
    text: String,
    selected: Boolean = false
) {
    Surface(
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        color = if (selected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.surface
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (selected)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun ColorChip(
    text: String,
    selected: Boolean = false
) {
    Surface(
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        color = if (selected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onBackground)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = text,
                color = if (selected)
                    MaterialTheme.colorScheme.onPrimary
                else
                    MaterialTheme.colorScheme.primary
            )
        }
    }
}
