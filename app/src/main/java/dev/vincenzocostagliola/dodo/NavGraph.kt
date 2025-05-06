package dev.vincenzocostagliola.dodo

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.vincenzocostagliola.data.navigation.NavigationRoute
import dev.vincenzocostagliola.home.ui.HomeScreen
import dev.vincenzocostagliola.home.ui.HomeViewModel


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoute.Home.route) {
        composable(NavigationRoute.Home.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            val navigateToDetail : () -> Unit = {  }
            HomeScreen(viewModel, navigateToDetail)
        }
/*
        composable(
            route = NavigationRoute.DetailsScreen.route,
            arguments = listOf(navArgument(NavigationRoute.DetailsScreen.argumentId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val coinId =
                backStackEntry.arguments?.getString(NavigationRoute.DetailsScreen.argumentId)

            Timber.d("Navigation - received coinId = $coinId")
            val viewModel = hiltViewModel<DetailsScreenViewModel>()
            val onBackPressed: () -> Unit = { navController.popBackStack() }
            DetailsScreen(
                viewModel = viewModel,
                navigationController = navController,
                coinId = coinId,
                onBackPressed = onBackPressed
            )
        }

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