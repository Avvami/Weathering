package com.personal.weathering.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.personal.weathering.MainViewModel
import com.personal.weathering.core.presentation.components.animatedComposable
import com.personal.weathering.core.util.WindowInfo
import com.personal.weathering.search.presentation.SearchScreen
import com.personal.weathering.settings.presentation.SettingsScreen
import com.personal.weathering.weather.presenation.forecast.WeeklyForecastScreen
import com.personal.weathering.weather.presenation.weather.WeatherScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootNavigationGraph(
    windowInfo: () -> WindowInfo,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    requestPermissions: () -> Unit
) {
    val navigateBack: () -> Unit = {
        navController.navigateUp()
    }
    val preferencesState = mainViewModel.preferencesState.collectAsStateWithLifecycle()
    val favoritesState = mainViewModel.favoritesState.collectAsStateWithLifecycle()
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
                preferencesState = preferencesState,
                favoritesState = favoritesState,
                weatherState = mainViewModel::weatherState,
                aqState = mainViewModel::aqState,
                pullToRefreshState = mainViewModel::pullToRefreshState,
                navigateToSettingsScreen = { navController.navigate(RootNavGraph.SETTINGS) },
                navigateToForecastScreen = { dayOfWeek -> navController.navigate(RootNavGraph.WEATHER_DETAILS + "/$dayOfWeek") },
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
            WeeklyForecastScreen(
                windowInfo = windowInfo,
                preferencesState = preferencesState,
                navigateBack = navigateBack,
                weatherState = mainViewModel.weatherState
            )
        }
        composable(
            route = RootNavGraph.SEARCH,
            enterTransition = { fadeIn() + scaleIn(initialScale = .9f) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 150)) + scaleOut(targetScale = .9f) }
        ) {
            SearchScreen(
                preferencesState = preferencesState,
                favoritesState = favoritesState,
                navigateBack = navigateBack,
                uiEvent = mainViewModel::uiEvent
            )
        }
        animatedComposable(
            route = RootNavGraph.SETTINGS
        ) {
            SettingsScreen(
                preferencesState = preferencesState,
                windowInfo = windowInfo,
                navigateBack = navigateBack,
                uiEvent = mainViewModel::uiEvent
            )
        }
    }
}

object RootNavGraph {
    const val ROOT = "root_graph"

    const val WEATHER = "weather_screen"
    const val WEATHER_DETAILS = "weather_details_screen"
    const val SEARCH = "search_screen"
    const val SETTINGS = "settings_screen"
}