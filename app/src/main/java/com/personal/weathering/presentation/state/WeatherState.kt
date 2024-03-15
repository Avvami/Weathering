package com.personal.weathering.presentation.state

import com.personal.weathering.domain.models.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val retrievingLocation: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)