package com.revibe.feature.catalog.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.revibe.core.ui.R as CoreR

@Composable
fun CatalogTopBar(
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