package dev.vincenzocostagliola.dodo

import dev.vincenzocostagliola.dodo.NavigationRoute.DetailsScreen.argumentId

internal sealed class NavigationRoute() {
    abstract val route: String

    data object Home : NavigationRoute() {
        override val route: String = "Home"
    }

    data object DetailsScreen : NavigationRoute() {
        fun createRoute(id: Int?) ="$screenName?$argumentId=${id}"
        fun createRouteWithNullable() = screenName
        const val argumentId: String = "id"
        private val screenName: String = "DetailScreen"
        override val route: String = "$screenName?$argumentId={$argumentId}"
    }

    data object SettingsScreen : NavigationRoute() {
        fun createRoute() = screenName
        private const val screenName: String = "SettingsScreen"
        override val route: String = screenName
    }
}