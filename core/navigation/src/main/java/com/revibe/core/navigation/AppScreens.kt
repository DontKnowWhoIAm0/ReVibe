package com.revibe.core.navigation

sealed class AppScreens(val route: String) {
    object Login : AppScreens("login")
    object Registration : AppScreens("registration")
    object Catalog : AppScreens("catalog")
}