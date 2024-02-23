package com.personal.weathering.presentation.ui.screens.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.personal.weathering.presentation.WeatherState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun WeatherScreen(
    navigator: DestinationsNavigator,
    state: WeatherState,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
        ) {
            CurrentWeatherData(
                navigator = navigator,
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )
            WeeklyForecast(navigator = navigator,state = state)
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}