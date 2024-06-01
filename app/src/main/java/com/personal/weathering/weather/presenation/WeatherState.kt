package com.personal.weathering.weather.presenation

import com.personal.weathering.weather.domain.models.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val retrievingLocation: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)