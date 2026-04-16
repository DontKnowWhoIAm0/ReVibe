package com.revibe.feature.product_details.presentation


sealed interface ProductEffect {

    data object NavigateBack : ProductEffect
    data object NavigateToCreateOutfit : ProductEffect
    data object NavigateToViewOutfit : ProductEffect

    data class ShowMessage(val message: String) : ProductEffect

}
