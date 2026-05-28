package com.revibe.feature.filters.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.revibe.core.domain.enums.*
import com.revibe.feature.catalog.presentation.FiltersState
import com.revibe.core.domain.enums.Color as AppColor

@Composable
fun FiltersScreen(
    initial: FiltersState = FiltersState(),
    onBackClick: () -> Unit = {},
    onApply: (FiltersState) -> Unit = {},
    onReset: () -> Unit = {}
) {
    var categories by remember { mutableStateOf(initial.selectedCategories) }
    var genders by remember { mutableStateOf(initial.selectedGenders) }
    var sizes by remember { mutableStateOf(initial.selectedSizes) }
    var colors by remember { mutableStateOf(initial.selectedColors) }
    var brands by remember { mutableStateOf(initial.selectedBrands) }
    var conditions by remember { mutableStateOf(initial.selectedConditions) }
    var priceRange by remember {
        mutableStateOf(
            initial.minPrice.toFloat()..
                    initial.maxPrice.coerceAtMost(100_000).toFloat()
        )
    }

    var showCategorySheet by remember { mutableStateOf(false) }
    var showBrandSheet by remember { mutableStateOf(false) }

    val hasFilters =
        categories.isNotEmpty() || genders.isNotEmpty() || sizes.isNotEmpty() || colors.isNotEmpty()
                || brands.isNotEmpty() || conditions.isNotEmpty() || priceRange != 0f..100_000f

    if (showCategorySheet) {
        MultiSelectBottomSheet(
            title = "Категория",
            items = Category.entries.map { it.displayName },
            selected = categories,
            onDismiss = { showCategorySheet = false },
            onConfirm = { categories = it; showCategorySheet = false }
        )
    }

    if (showBrandSheet) {
        MultiSelectBottomSheet(
            title = "Бренд",
            items = Brand.entries.map { it.displayName },
            selected = brands,
            onDismiss = { showBrandSheet = false },
            onConfirm = { brands = it; showBrandSheet = false }
        )
    }


    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                Spacer(Modifier.width(12.dp))
                Text("Фильтры", style = MaterialTheme.typography.titleLarge)
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        onApply(
                            FiltersState(
                                selectedCategories = categories,
                                selectedGenders = genders,
                                selectedSizes = sizes,
                                selectedColors = colors,
                                selectedBrands = brands,
                                selectedConditions = conditions,
                                minPrice = priceRange.start.toInt(),
                                maxPrice = priceRange.endInclusive.toInt()
                            )
                        )
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(50)
                ) { Text("Применить") }

                if (hasFilters) {
                    OutlinedButton(
                        onClick = {
                            categories = emptySet()
                            genders = emptySet()
                            sizes = emptySet()
                            colors = emptySet()
                            brands = emptySet()
                            conditions = emptySet()
                            priceRange = 0f..100_000f
                            onReset()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) { Text("Сбросить") }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            FilterSection(
                title = "Категория",
                actionLabel = if (categories.isEmpty()) "Посмотреть все"
                else "Выбрано: ${categories.size}",
                onActionClick = { showCategorySheet = true }
            )

            FilterSection(title = "Пол") {
                ChipRow {
                    Gender.entries.forEach { g ->
                        SelectableChip(
                            text = g.displayName,
                            selected = g.displayName in genders,
                            onClick = { genders = genders.toggle(g.displayName) }
                        )
                    }
                }
            }


            FilterSection(title = "Размер") {
                ChipRow {
                    Size.entries.forEach { s ->
                        SelectableChip(
                            text = s.displayName,
                            selected = s.displayName in sizes,
                            onClick = { sizes = sizes.toggle(s.displayName) }
                        )
                    }
                }
            }

            FilterSection(title = "Цвет") {
                ChipRow {
                    AppColor.entries.forEach { c ->
                        ColorChip(
                            text = c.displayName,
                            color = COLOR_SWATCHES[c.displayName] ?: Color.Gray,
                            selected = c.displayName in colors,
                            onClick = { colors = colors.toggle(c.displayName) }
                        )
                    }
                }
            }

            FilterSection(
                title = "Бренд",
                actionLabel = if (brands.isEmpty()) "Посмотреть все"
                else "Выбрано: ${brands.size}",
                onActionClick = { showBrandSheet = true }
            )

            FilterSection(title = "Состояние") {
                ChipRow {
                    Condition.entries.forEach { cond ->
                        SelectableChip(
                            text = cond.displayName,
                            selected = cond.displayName in conditions,
                            onClick = { conditions = conditions.toggle(cond.displayName) }
                        )
                    }
                }
            }

            FilterSection(title = "Цена") {
                Text(
                    text = "от ${priceRange.start.toInt()} до ${priceRange.endInclusive.toInt()} рублей",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                RangeSlider(
                    value = priceRange,
                    onValueChange = { priceRange = it },
                    valueRange = 0f..100_000f
                )
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun FilterSection(
    title: String,
    actionLabel: String,
    onActionClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleSmall, fontSize = 18.sp)
        Spacer(Modifier.weight(1f))
        TextButton(onClick = onActionClick) {
            Text(actionLabel, color = MaterialTheme.colorScheme.primary)
        }
    }
}

private fun <T> Set<T>.toggle(item: T): Set<T> =
    if (item in this) this - item else this + item



private val COLOR_SWATCHES: Map<String, Color> = mapOf(
    AppColor.WHITE.displayName to Color.White,
    AppColor.BLACK.displayName to Color.Black,
    AppColor.GREY.displayName to Color.Gray,
    AppColor.BLUE.displayName to Color.Blue,
    AppColor.NAVY.displayName to Color(0xFF001F5B),
    AppColor.RED.displayName to Color.Red,
    AppColor.GREEN.displayName to Color.Green,
    AppColor.YELLOW.displayName to Color.Yellow,
    AppColor.BROWN.displayName to Color(0xFF795548),
    AppColor.BEIGE.displayName to Color(0xFFF5F5DC),
    AppColor.ORANGE.displayName to Color(0xFFFF9800),
    AppColor.PURPLE.displayName to Color(0xFF9C27B0),
    AppColor.PINK.displayName to Color(0xFFE91E63),
    AppColor.TURQUOISE.displayName to Color(0xFF00BCD4),
    AppColor.LIME.displayName to Color(0xFF8BC34A),
    AppColor.OLIVE.displayName to Color(0xFF808000),
    AppColor.CORAL.displayName to Color(0xFFFF7F7F),
    AppColor.GOLD.displayName to Color(0xFFFFD700),
    AppColor.SILVER.displayName to Color(0xFFC0C0C0),
    AppColor.BRONZE.displayName to Color(0xFFCD7F32),
    AppColor.MINT.displayName to Color(0xFF98FF98),
    AppColor.LAVENDER.displayName to Color(0xFFE6E6FA),
    AppColor.PEACH.displayName to Color(0xFFFFDAB9),
    AppColor.INDIGO.displayName to Color(0xFF4B0082),
)

