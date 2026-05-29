package com.revibe.core.navigation

sealed class AppScreens(val route: String) {
    object Login : AppScreens("login")
    object Registration : AppScreens("registration")
    object Catalog : AppScreens("catalog")
    object Favourites : AppScreens("favourites")
    object Cart : AppScreens("cart")
    object Profile : AppScreens("profile")
    object ProductDetails : AppScreens("product_details/{article}/{isFavourite}") {
        fun createRoute(article: String, isFavourite: Boolean) =
            "product_details/$article/$isFavourite"
    }

    object Filters : AppScreens("filters")
    object Search : AppScreens("search")
}
