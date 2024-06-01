package com.personal.weathering.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.personal.weathering.MainViewModel
import com.personal.weathering.aq.presentation.AqScreen
import com.personal.weathering.core.util.WindowInfo
import com.personal.weathering.search.presentation.SearchScreen
import com.personal.weathering.weather.presenation.weather.WeatherScreen
import com.personal.weathering.weather.presenation.weather_details.WeatherDetailsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootNavigationGraph(
    windowInfo: () -> WindowInfo,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    requestPermissions: () -> Unit
) {
    NavHost(
        navController = navController,
        route = RootNavGraph.ROOT,
        startDestination = RootNavGraph.WEATHER,
    ) {
        composable(
            route = RootNavGraph.WEATHER,
            enterTransition = { scaleIn(initialScale = .98f) },
            exitTransition = { scaleOut(targetScale = .98f) }
        ) {
            WeatherScreen(
                windowInfo = windowInfo,
                isNetworkConnected = mainViewModel::isNetworkConnected,
                preferencesState = mainViewModel.preferencesState.collectAsStateWithLifecycle(),
                favoritesState = mainViewModel.favoritesState.collectAsStateWithLifecycle(),
                weatherState = mainViewModel::weatherState,
                aqState = mainViewModel::aqState,
                pullToRefreshState = mainViewModel::pullToRefreshState,
                navigateToWeatherDetailsScreen = { dayOfWeek -> navController.navigate(RootNavGraph.WEATHER_DETAILS + "/$dayOfWeek") },
                navigateToAqScreen = { navController.navigate(RootNavGraph.AQ) },
                navigateToSearchScreen = { navController.navigate(RootNavGraph.SEARCH) },
                requestPermissions = requestPermissions,
                uiEvent = mainViewModel::uiEvent
            )
        }
        composable(
            route = RootNavGraph.WEATHER_DETAILS + "/{dayOfWeek}",
            arguments = listOf(navArgument("dayOfWeek") { type = NavType.IntType; nullable = false }),
            enterTransition = { fadeIn() + scaleIn(initialScale = .9f) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 150)) + scaleOut(targetScale = .9f) }
        ) {
            WeatherDetailsScreen(
                windowInfo = windowInfo,
                preferencesState = mainViewModel.preferencesState.collectAsStateWithLifecycle(),
                navigateBack = { if (navController.canGoBack) navController.popBackStack() },
                weatherState = mainViewModel::weatherState
            )
        }
        composable(
            route = RootNavGraph.AQ,
            enterTransition = {
                slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start)
            },
            exitTransition = {
                slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End)
            }
        ) {
            AqScreen(
                windowInfo = windowInfo,
                preferencesState = mainViewModel.preferencesState.collectAsStateWithLifecycle(),
                aqState = mainViewModel::aqState,
                pullToRefreshState = mainViewModel::pullToRefreshState,
                navigateBack = { if (navController.canGoBack) navController.popBackStack() },
                uiEvent = mainViewModel::uiEvent
            )
        }
        composable(
            route = RootNavGraph.SEARCH,
            enterTransition = { fadeIn() + scaleIn(initialScale = .9f) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 150)) + scaleOut(targetScale = .9f) }
        ) {
            SearchScreen(
                preferencesState = mainViewModel.preferencesState.collectAsStateWithLifecycle(),
                favoritesState = mainViewModel.favoritesState.collectAsStateWithLifecycle(),
                navigateBack = { if (navController.canGoBack) navController.popBackStack() },
                uiEvent = mainViewModel::uiEvent
            )
        }
    }
}

object RootNavGraph {
    const val ROOT = "root_graph"

    const val WEATHER = "weather_screen"
    const val WEATHER_DETAILS = "weather_details_screen"
    const val AQ = "aq_screen"
    const val SEARCH = "search_screen"
}

val NavHostController.canGoBack: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED