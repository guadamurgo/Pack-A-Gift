package com.packagift.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.packagift.app.ui.screens.cart.CartScreen
import com.packagift.app.ui.screens.create.CreateScreen
import com.packagift.app.ui.screens.detail.PackDetailScreen
import com.packagift.app.ui.screens.highlights.HighlightsScreen
import com.packagift.app.ui.screens.profile.ProfileScreen
import com.packagift.app.ui.theme.BackgroundPink
import com.packagift.app.ui.theme.PinkPrimary
import com.packagift.app.ui.theme.TextSecondary
import com.packagift.app.ui.theme.WineSecondary
import com.packagift.app.ui.viewmodel.PackAGiftViewModel

private data class BottomItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val bottomItems = listOf(
    BottomItem(Routes.HIGHLIGHTS, "Destacados", Icons.Filled.Star),
    BottomItem(Routes.CREATE, "Crear", Icons.Filled.AddCircle),
    BottomItem(Routes.PROFILE, "Perfil", Icons.Filled.Person)
)

@Composable
fun PackAGiftApp(viewModel: PackAGiftViewModel) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute in bottomItems.map { it.route }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (showBottomBar) {
                BottomBar(
                    currentRoute = currentRoute,
                    onSelect = { route ->
                        if (currentRoute != route) {
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HIGHLIGHTS,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HIGHLIGHTS) {
                HighlightsScreen(
                    viewModel = viewModel,
                    onPackClick = { pack -> navController.navigate(Routes.packDetail(pack.id)) },
                    onCartClick = { navController.navigate(Routes.CART) }
                )
            }
            composable(Routes.CREATE) {
                CreateScreen(
                    viewModel = viewModel,
                    onCartClick = { navController.navigate(Routes.CART) }
                )
            }
            composable(Routes.PROFILE) {
                ProfileScreen(
                    viewModel = viewModel,
                    onPackClick = { pack -> navController.navigate(Routes.packDetail(pack.id)) }
                )
            }
            composable(Routes.CART) {
                CartScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
            composable(
                route = Routes.PACK_DETAIL,
                arguments = listOf(navArgument("packId") { type = NavType.StringType })
            ) { entry ->
                PackDetailScreen(
                    viewModel = viewModel,
                    packId = entry.arguments?.getString("packId"),
                    onBack = { navController.popBackStack() },
                    onCartClick = { navController.navigate(Routes.CART) }
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    currentRoute: String?,
    onSelect: (String) -> Unit
) {
    NavigationBar(
        containerColor = BackgroundPink,
        tonalElevation = 0.dp
    ) {
        bottomItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onSelect(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = {
                    Text(item.label, style = MaterialTheme.typography.labelMedium)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = WineSecondary,
                    selectedTextColor = WineSecondary,
                    indicatorColor = PinkPrimary.copy(alpha = 0.5f),
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )
        }
    }
}
