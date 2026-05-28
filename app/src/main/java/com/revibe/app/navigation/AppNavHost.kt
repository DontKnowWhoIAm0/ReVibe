package com.revibe.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.revibe.feature.catalog.presentation.FiltersState
import com.revibe.feature.favourites.di.DaggerFavouritesComponent
import com.revibe.feature.favourites.di.FavouritesDependencies
import com.revibe.feature.favourites.presentation.components.FavouritesScreen
import com.revibe.feature.filters.presentation.components.FiltersScreen
import com.revibe.feature.product_details.di.DaggerProductDetailsComponent
import com.revibe.feature.product_details.di.ProductDetailsDependencies
import com.revibe.feature.registration.di.RegistrationDependencies
import kotlinx.coroutines.runBlocking

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

        composable(AppScreens.Catalog.route) { backStackEntry ->
            val userId = remember {
                runBlocking { app.tokenDataStore.getUserId() ?: "" }
            }


            val catalogViewModel = remember {
                DaggerCatalogComponent.factory()
                    .create(object : CatalogDependencies {
                        override fun retrofit() = app.networkComponent.retrofit()
                    }, userId = userId)
                    .catalogViewModel()
            }

            val filters = backStackEntry.savedStateHandle
                .getStateFlow<FiltersState?>("filters", null)
                .collectAsState()

            LaunchedEffect(filters.value) {
                filters.value?.let { catalogViewModel.applyFilters(it) }
            }

            CatalogScreen(
                viewModel = catalogViewModel,
                onProductClick = { product ->
                    navController.navigate(AppScreens.ProductDetails.createRoute(product.article.toString(), false))
                },
                onSearchClick = { /* TODO */ },
                onFilterClick = { navController.navigate(AppScreens.Filters.route) }
            )
        }

        composable(AppScreens.ProductDetails.route) { backStackEntry ->
            val article = backStackEntry.arguments?.getString("article") ?: return@composable
            val isFavourite = backStackEntry.arguments?.getString("isFavourite")?.toBooleanStrictOrNull() ?: false
            val userId = remember {
                runBlocking { app.tokenDataStore.getUserId() ?: "" }
            }

            val viewModel = remember(article) {
                DaggerProductDetailsComponent.factory()
                    .create(
                        dependencies = object : ProductDetailsDependencies {
                            override fun retrofit() = app.networkComponent.retrofit()
                        },
                        article = article,
                        userId = userId,
                        isFavourite = isFavourite
                    )
                    .productDetailsViewModel()
            }

            ProductScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onCreateOutfitClick = { /* TODO */ },
                onViewOutfitClick = { /* TODO */ },
                onFavoriteClick = { viewModel.toggleFavourite() }
            )
        }

        composable(AppScreens.Favourites.route) {
            val userId = remember {
                runBlocking { app.tokenDataStore.getUserId() ?: "" }
            }

            val favouritesViewModel = remember {
                DaggerFavouritesComponent.factory()
                    .create(object : FavouritesDependencies {
                        override fun retrofit() = app.networkComponent.retrofit()
                    },
                        userId = userId
                    )
                    .favouritesViewModel()
            }

            FavouritesScreen(
                viewModel = favouritesViewModel,
                onProductClick = { product ->
                    navController.navigate(
                        AppScreens.ProductDetails.createRoute(product.article.toString(), true)
                    )
                },
                onCartClick = { /* TODO */ }
            )
        }

        composable(AppScreens.Filters.route) {

            val currentFilters = navController
                .getBackStackEntry(AppScreens.Catalog.route)
                .savedStateHandle
                .get<FiltersState>("filters") ?: FiltersState()

            FiltersScreen(
                initial = currentFilters,
                onBackClick = { navController.popBackStack() },
                onApply = { filters ->
                    navController.previousBackStackEntry ?.savedStateHandle ?.set("filters", filters)
                    navController.popBackStack()
                },
                onReset = {
                    navController.previousBackStackEntry ?.savedStateHandle ?.set("filters", FiltersState())
                    navController.popBackStack()
                }
            )
        }



        composable(AppScreens.Cart.route) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Корзина — в разработке")
            }
        }

        composable(AppScreens.Profile.route) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Профиль — в разработке")
            }
        }
    }
}


