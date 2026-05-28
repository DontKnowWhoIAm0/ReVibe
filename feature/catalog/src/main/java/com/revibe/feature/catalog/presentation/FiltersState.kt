package com.revibe.feature.catalog.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FiltersState(
    val selectedCategories: Set<String> = emptySet(),
    val selectedGenders: Set<String> = emptySet(),
    val selectedSizes: Set<String> = emptySet(),
    val selectedColors: Set<String> = emptySet(),
    val selectedBrands: Set<String> = emptySet(),
    val selectedConditions: Set<String> = emptySet(),
    val minPrice: Int = 0,
    val maxPrice: Int = Int.MAX_VALUE
) : Parcelable {
    val isEmpty: Boolean get() = selectedGenders.isEmpty() && selectedSizes.isEmpty() && selectedColors.isEmpty()
            && selectedBrands.isEmpty() && selectedConditions.isEmpty() && minPrice == 0 && maxPrice == Int.MAX_VALUE
}
