package dev.vincenzocostagliola.dodo

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.vincenzocostagliola.home.ui.HomeScreen
import dev.vincenzocostagliola.home.ui.HomeViewModel
import timber.log.Timber
import androidx.navigation.navArgument
import dev.vincenzocostagliola.details.ui.DetailsViewModel
import dev.vincenzocostagliola.details.ui.DetailsScreen
import dev.vincenzocostagliola.settings.ui.SettingsScreen
import dev.vincenzocostagliola.settings.ui.SettingsScreenViewModel


@Composable
internal fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoute.Home.route) {
        composable(NavigationRoute.Home.route) {
            Timber.d("Navigation - navigate to ${NavigationRoute.Home.route}")

            val viewModel = hiltViewModel<HomeViewModel>()
            val navigateToDetail: (Int?) -> Unit = {
                val route = if (it != null) {
                    NavigationRoute.DetailsScreen.createRoute(it)
                } else {
                    NavigationRoute.DetailsScreen.createRouteWithNullable()
                }
                navController.navigate(route)
            }
            val openSettings: () -> Unit =
                { navController.navigate(NavigationRoute.SettingsScreen.createRoute()) }

            HomeScreen(
                viewModel = viewModel,
                navigateToDetail = navigateToDetail,
                openSettings = openSettings
            )
        }

        composable(
            route = NavigationRoute.DetailsScreen.route,
            arguments = listOf(navArgument(NavigationRoute.DetailsScreen.argumentId) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            Timber.d("Navigation - navigate to ${NavigationRoute.DetailsScreen.route}")

            val id: Int? =
                backStackEntry.arguments?.getString(
                    NavigationRoute.DetailsScreen.argumentId
                )?.toIntOrNull()

            Timber.d("Navigation - received id = $id")
            val viewModel = hiltViewModel<DetailsViewModel>()
            val onBackPressed: () -> Unit = { navController.popBackStack() }
            DetailsScreen(
                viewModel = viewModel,
                idToShow = id,
                onBackPressed = onBackPressed
            )
        }


        composable(
            route = NavigationRoute.SettingsScreen.route,
        ) { backStackEntry ->
            Timber.d("Navigation - navigate to ${NavigationRoute.SettingsScreen.route}")
            val viewModel = hiltViewModel<SettingsScreenViewModel>()
            val onBackPressed: () -> Unit = { navController.popBackStack() }
            SettingsScreen(
                viewModel = viewModel,
                onBackPressed = onBackPressed
            )
        }
    }
}