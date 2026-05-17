package com.revibe.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.revibe.app.ReVibe
import com.revibe.core.data.di.DataStoreModule
import com.revibe.core.navigation.AppScreens
import com.revibe.feature.catalog.di.CatalogDependencies
import com.revibe.feature.catalog.presentation.components.CatalogScreen
import com.revibe.feature.login.presentation.components.LoginScreen
import com.revibe.feature.registration.presentation.components.RegistrationScreen
import com.revibe.feature.login.di.LoginDependencies
import com.revibe.feature.product_details.presentation.components.ProductScreen
import com.revibe.feature.registration.di.DaggerRegistrationComponent
import com.revibe.feature.login.di.DaggerLoginComponent
import com.revibe.feature.catalog.di.DaggerCatalogComponent
import com.revibe.feature.product_details.di.DaggerProductDetailsComponent
import com.revibe.feature.product_details.di.ProductDetailsDependencies
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
        composable(AppScreens.Login.route) {

            val loginViewModel = remember {
                DaggerLoginComponent.factory()
                    .create(object : LoginDependencies {
                        override fun retrofit() = app.networkComponent.retrofit()
                    },
                        dataStoreModule = DataStoreModule(app)
                    )
                    .loginViewModel()
            }

            LoginScreen(
                viewModel = loginViewModel,
                onRegisterClick = { navController.navigate(AppScreens.Registration.route) },
                onLoginSuccess = {
                    navController.navigate(AppScreens.Catalog.route) {
                        popUpTo(AppScreens.Login.route) { inclusive = true }
                    }
                }
            )

        }

        composable(AppScreens.Registration.route) {

            val registrationViewModel = remember {
                DaggerRegistrationComponent.factory()
                    .create(object : RegistrationDependencies {
                        override fun retrofit() = app.networkComponent.retrofit()
                    },
                        dataStoreModule = DataStoreModule(app)
                    )
                    .registrationViewModel()
            }

            RegistrationScreen(
                viewModel = registrationViewModel,
                onLoginClick = { navController.navigate(AppScreens.Login.route) },
                onRegistrationSuccess = {
                    navController.navigate(AppScreens.Catalog.route) {
                        popUpTo(AppScreens.Registration.route) { inclusive = true }
                    }
                }
            )
        }

        composable(AppScreens.Catalog.route) {
            val catalogViewModel = remember {
                DaggerCatalogComponent.factory()
                    .create(object : CatalogDependencies {
                        override fun retrofit() = app.networkComponent.retrofit()
                    })
                    .catalogViewModel()
            }

            CatalogScreen(
                viewModel = catalogViewModel,
                onProductClick = { product ->
                    navController.navigate(AppScreens.ProductDetails.createRoute(product.article.toString()))
                },
                onFavoriteClick = { /* TODO */ },
                onSearchClick = { /* TODO */ },
                onFilterClick = { /* TODO */ }
            )
        }

        composable(AppScreens.ProductDetails.route) { backStackEntry ->
            val article = backStackEntry.arguments?.getString("article") ?: return@composable

            val viewModel = remember(article) {
                DaggerProductDetailsComponent.factory()
                    .create(
                        dependencies = object : ProductDetailsDependencies {
                            override fun retrofit() = app.networkComponent.retrofit()
                        },
                        article = article
                    )
                    .productDetailsViewModel()
            }

            ProductScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onCreateOutfitClick = { /* TODO */ },
                onViewOutfitClick = { /* TODO */ },
                onFavoriteClick = { /* TODO */ }
            )
        }
    }
}