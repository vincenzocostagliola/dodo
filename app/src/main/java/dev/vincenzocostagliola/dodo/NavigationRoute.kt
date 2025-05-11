package dev.vincenzocostagliola.dodo

internal sealed class NavigationRoute() {
    abstract val route: String

    data object Home : NavigationRoute() {
        override val route: String = "Home"
    }

    data object DetailsScreen : NavigationRoute() {
        fun createRoute(id: Int) = "$screenName?$argumentId=${id}"
        const val argumentId: String = "id"
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