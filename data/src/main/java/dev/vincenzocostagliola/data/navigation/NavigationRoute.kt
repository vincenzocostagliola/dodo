package dev.vincenzocostagliola.data.navigation

sealed class NavigationRoute() {
    abstract val route: String

    data object Home : NavigationRoute() {
        override val route: String = "Home"
    }

    data object DetailsScreen : NavigationRoute() {
        fun createRoute(coinId: String) = "$screenName?$argumentId=${coinId}"
        const val argumentId: String = "coinId"
        private val screenName: String = "DetailScreen"
        override val route: String = "$screenName?$argumentId={$argumentId}"
    }

    data object DescriptionScreen : NavigationRoute() {
        fun createRoute(description: String) = "$screenName?$argumentId=${description}"
        const val argumentId: String = "description"
        private const val screenName: String = "DescriptionScreen"
        override val route: String = "$screenName?$argumentId={$argumentId}"
    }
}