package com.personal.weatherapp.presentation

import androidx.compose.ui.graphics.Color
import com.personal.weatherapp.domain.airquality.AQInfo
import com.personal.weatherapp.domain.weather.WeatherInfo
import com.personal.weatherapp.presentation.ui.theme.*

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val aqInfo: AQInfo? = null,
    val isLoading: Boolean = false,
    val weatherError: String? = null,
    val aqError: String? = null,
    val openAlertDialog: Boolean = false,
    val surfaceColor: Color = colorSurfaceWeather,
    val onSurfaceColor: Color = colorOnSurfaceWeather,
    val plainTextColor: Color = colorPlainTextWeather
)