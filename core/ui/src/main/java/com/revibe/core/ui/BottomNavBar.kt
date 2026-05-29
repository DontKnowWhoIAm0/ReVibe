package com.revibe.core.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.revibe.core.navigation.AppScreens

private data class BottomNavItem(
    val screen: AppScreens,
    val iconRes: Int,
    val activeIconRes: Int,
    val label: String
)

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(AppScreens.Catalog, R.drawable.catalog, R.drawable.catalog_active, "Каталог"),
        BottomNavItem(AppScreens.Favourites, R.drawable.favourite_full, R.drawable.favourite_active, "Избранное"),
        BottomNavItem(AppScreens.Cart, R.drawable.cart, R.drawable.cart_active, "Корзина"),
        BottomNavItem(AppScreens.Profile, R.drawable.profile, R.drawable.profile_active, "Профиль"),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.screen.route

            Row(
                modifier = Modifier
                    .animateContentSize()
                    .then(
                        if (isSelected) Modifier
                            .background(
                                MaterialTheme.colorScheme.secondary,
                                RoundedCornerShape(50)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                        else Modifier.padding(8.dp)
                    )
                    .clickable {
                        if (currentRoute != item.screen.route) {
                            navController.navigate(item.screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    painter = painterResource(
                        if (isSelected) item.activeIconRes else item.iconRes
                    ),
                    contentDescription = item.label,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
                if (isSelected) {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}
