package com.revibe.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.revibe.core.navigation.AppScreens
import com.revibe.feature.login.presentation.LoginScreen
import com.revibe.feature.registration.presentation.RegistrationScreen
import com.revibe.feature.catalog.presentation.CatalogScreen

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Catalog.route
    ) {
        composable(AppScreens.Login.route) { LoginScreen() }
        composable(AppScreens.Registration.route) { RegistrationScreen() }
        composable(AppScreens.Catalog.route) { CatalogScreen(products = listOf("200 ₽", "200 ₽", "200 ₽", "200 ₽", "200 ₽", "200 ₽")) }
    }
}