package com.personal.weatherapp.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.personal.weatherapp.presentation.ui.screens.HomeScreen
import com.personal.weatherapp.presentation.ui.screens.NavGraphs
import com.personal.weatherapp.presentation.ui.screens.destinations.CurrentWeatherDetailsScreenDestination
import com.personal.weatherapp.presentation.ui.screens.destinations.HomeScreenDestination
import com.personal.weatherapp.presentation.ui.screens.destinations.WeeklyForecastDetailsScreenDestination
import com.personal.weatherapp.presentation.ui.screens.weather.CurrentWeatherDetailsScreen
import com.personal.weatherapp.presentation.ui.screens.weather.WeeklyForecastDetailsScreen
import com.personal.weatherapp.presentation.ui.theme.WeatherAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ))
        setContent {
            WeatherAppTheme {
                DestinationsNavHost(navGraph = NavGraphs.root) {
                    composable(HomeScreenDestination) {
                        HomeScreen(
                            navigator = destinationsNavigator,
                            state = viewModel.state,
                            modifier = Modifier
                                .fillMaxSize()
                                .navigationBarsPadding(),
                            uiEvent = viewModel::uiEvent
                        )
                    }
                    composable(CurrentWeatherDetailsScreenDestination) {
                        CurrentWeatherDetailsScreen(
                            navigator = destinationsNavigator,
                            state = viewModel.state,
                            uiEvent = viewModel::uiEvent
                        )
                    }
                    composable(WeeklyForecastDetailsScreenDestination) {
                        WeeklyForecastDetailsScreen(
                            navigator = destinationsNavigator,
                            state = viewModel.state,
                            uiEvent = viewModel::uiEvent
                        )
                    }
                }
            }
        }
    }
}