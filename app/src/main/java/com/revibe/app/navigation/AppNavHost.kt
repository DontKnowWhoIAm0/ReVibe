package com.revibe.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.revibe.app.ReVibe
import com.revibe.core.navigation.AppScreens
import com.revibe.feature.login.presentation.LoginScreen
import com.revibe.feature.registration.presentation.components.RegistrationScreen
import com.revibe.feature.catalog.presentation.CatalogScreen
import com.revibe.feature.product_details.presentation.components.ProductScreen
import com.revibe.feature.registration.di.DaggerRegistrationComponent
import com.revibe.feature.registration.di.RegistrationDependencies

@Composable
fun AppNavHost(
    navController: NavHostController,
    app: ReVibe
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Registration.route
    ) {
        composable(AppScreens.Login.route) { LoginScreen() }

        composable(AppScreens.Registration.route) {

            val registrationViewModel = remember {
                DaggerRegistrationComponent.factory()
                    .create(object : RegistrationDependencies {
                        override fun retrofit() = app.networkComponent.retrofit()
                    })
                    .registrationViewModel()
            }

            RegistrationScreen(
                viewModel = registrationViewModel,
                onRegisterClick = { registrationViewModel.register() },
                onLoginClick = { navController.navigate(AppScreens.Login.route) },
                onRegistrationSuccess = {
                    navController.navigate(AppScreens.Catalog.route) {
                        popUpTo(AppScreens.Registration.route) { inclusive = true }
                    }
                }
            )
        }

        composable(AppScreens.Catalog.route) { CatalogScreen(products = listOf("200 ₽", "200 ₽", "200 ₽", "200 ₽", "200 ₽", "200 ₽")) }

        composable(AppScreens.ProductDetails.route) { ProductScreen(
            onBackClick = { navController.popBackStack() },
            onCreateOutfitClick = { /* TODO */ },
            onViewOutfitClick = { /* TODO */ },
            onFavoriteClick = {},
            onAddToCartClick = {}
        ) }
    }
}