package com.personal.weatherapp.presentation.ui.screens.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.personal.weatherapp.presentation.WeatherState

@Composable
fun WeatherScreen(
    state: WeatherState,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            CurrentWeatherData(state = state, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp))
            WeeklyForecast(state = state)
        }
    }
}