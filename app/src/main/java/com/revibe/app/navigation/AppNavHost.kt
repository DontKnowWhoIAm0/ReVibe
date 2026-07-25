package com.revibe.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.revibe.app.ReVibe
import com.revibe.core.data.di.DataStoreModule
import com.revibe.core.db.ReVibeDatabase
import com.revibe.core.navigation.AppScreens
import com.revibe.feature.cart.di.CartDependencies
import com.revibe.feature.cart.di.DaggerCartComponent
import com.revibe.feature.cart.presentation.components.CartScreen
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
import com.revibe.feature.search.presentation.components.SearchScreen
import com.revibe.feature.product_details.di.DaggerProductDetailsComponent
import com.revibe.feature.product_details.di.ProductDetailsDependencies
import com.revibe.feature.profile.presentation.components.ProfileScreen
import com.revibe.feature.registration.di.RegistrationDependencies
import kotlinx.coroutines.runBlocking

@Composable
fun AppNavHost(
    navController: NavHostController,
    app: ReVibe
) {
    val userId = remember {
        runBlocking { app.tokenDataStore.getUserId() ?: "" }
    }

    val catalogViewModel = remember {
        DaggerCatalogComponent.factory()
            .create(object : CatalogDependencies {
                override fun retrofit() = app.networkComponent.retrofit()
                override fun context() = app
            }, userId = userId)
            .catalogViewModel()
    }

    val db = remember { ReVibeDatabase.getInstance(app) }

    val cartDao = remember { db.cartDao() }

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
                onSearchClick = { navController.navigate(AppScreens.Search.route) },
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
                            override fun context() = app
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
                onFavoriteClick = { viewModel.toggleFavourite() },
                onAddToCartClick = { viewModel.toggleCart() }
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
                        override fun context() = app
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

        composable(AppScreens.Search.route) {
            val catalogState by catalogViewModel.state.collectAsState()

            SearchScreen(
                initialQuery = catalogState.searchQuery,
                onBackClick = { navController.popBackStack() },
                onApply = { query ->
                    catalogViewModel.applySearch(query)
                    navController.popBackStack()
                },
                onReset = {
                    catalogViewModel.applySearch("")
                    navController.popBackStack()
                }
            )
        }

        composable(AppScreens.Profile.route) {

            val userName = remember {
                runBlocking { app.tokenDataStore.getUserName() ?: "Пользователь" }
            }

            ProfileScreen(
                userName = userName,
                onLogoutClick = {
                    navController.navigate(AppScreens.Login.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(AppScreens.Cart.route) {
            val userId = remember {
                runBlocking { app.tokenDataStore.getUserId() ?: "" }
            }

            val cartViewModel = remember {
                DaggerCartComponent.factory()
                    .create(
                        dependencies = object : com.revibe.feature.cart.di.CartDependencies {
                            override fun retrofit() = app.networkComponent.retrofit()
                            override fun context() = app
                        },
                        userId = userId
                    )
                    .cartViewModel()
            }

            CartScreen(
                viewModel = cartViewModel,
                onProductClick = { article ->
                    navController.navigate(AppScreens.ProductDetails.createRoute(article, false))
                }
            )
        }
    }
}
