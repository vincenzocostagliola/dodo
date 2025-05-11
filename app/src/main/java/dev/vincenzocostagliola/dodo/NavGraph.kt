package dev.vincenzocostagliola.dodo

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.vincenzocostagliola.dodo.NavigationRoute
import dev.vincenzocostagliola.home.ui.HomeScreen
import dev.vincenzocostagliola.home.ui.HomeViewModel
import timber.log.Timber
import androidx.navigation.navArgument
import dev.vincenzocostagliola.details.ui.DetailsViewModel
import dev.vincenzocostagliola.details.ui.DetailsScreen


@Composable
internal fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoute.Home.route) {
        composable(NavigationRoute.Home.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            val navigateToDetail: (Int) -> Unit = {
                navController.navigate(NavigationRoute.DetailsScreen.createRoute(it))
            }
            HomeScreen(viewModel, navigateToDetail)
        }

        composable(
            route = NavigationRoute.DetailsScreen.route,
            arguments = listOf(navArgument(NavigationRoute.DetailsScreen.argumentId) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val id: Int? =
                backStackEntry.arguments?.getInt(NavigationRoute.DetailsScreen.argumentId)

            Timber.d("Navigation - received id = $id")
            val viewModel = hiltViewModel<DetailsViewModel>()
            val onBackPressed: () -> Unit = { navController.popBackStack() }
            DetailsScreen(
                viewModel = viewModel,
                idToShow = id
            ) { onBackPressed }
        }

        /*
                composable(
                    route = NavigationRoute.DescriptionScreen.route,
                    arguments = listOf(navArgument(NavigationRoute.DescriptionScreen.argumentId) {
                        type = NavType.StringType
                    })
                ) { backStackEntry ->
                    val description =
                        backStackEntry.arguments?.getString(NavigationRoute.DescriptionScreen.argumentId)?.let {
                            URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
                        } ?: ""
                    Timber.d("Navigation - received Description = $description")

                    val onBackPressed: () -> Unit = { navController.popBackStack() }
                    DescriptionScreen(description, onBackPressed)
                }

         */

    }
}