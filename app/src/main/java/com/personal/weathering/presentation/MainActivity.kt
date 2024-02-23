package com.personal.weathering.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.personal.weathering.WeatheringApp
import com.personal.weathering.presentation.ui.screens.HomeScreen
import com.personal.weathering.presentation.ui.screens.NavGraphs
import com.personal.weathering.presentation.ui.screens.destinations.CurrentWeatherDetailsScreenDestination
import com.personal.weathering.presentation.ui.screens.destinations.HomeScreenDestination
import com.personal.weathering.presentation.ui.screens.destinations.WeeklyForecastDetailsScreenDestination
import com.personal.weathering.presentation.ui.screens.weather.CurrentWeatherDetailsScreen
import com.personal.weathering.presentation.ui.screens.weather.WeeklyForecastDetailsScreen
import com.personal.weathering.presentation.ui.theme.WeatheringTheme
import com.personal.weathering.presentation.ui.theme.colorSurfaceWeather
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable

class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels {
        viewModelFactory {
            WeatherViewModel(
                weatherRepository = WeatheringApp.appModule.weatherRepository,
                aqRepository = WeatheringApp.appModule.aqRepository,
                locationTracker = WeatheringApp.appModule.locationTracker
            )
        }
    }
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

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
            WeatheringTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().background(colorSurfaceWeather)
                ) {
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
}