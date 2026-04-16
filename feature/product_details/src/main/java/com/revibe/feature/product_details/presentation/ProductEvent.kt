package com.revibe.feature.product_details.presentation


sealed interface ProductEvent {
    data object OnBackClick : ProductEvent
    data object OnCreateOutfitClick : ProductEvent
    data object OnViewOutfitClick : ProductEvent
    data object OnFavoriteClick : ProductEvent
    data object OnAddToCartClick : ProductEvent
}
