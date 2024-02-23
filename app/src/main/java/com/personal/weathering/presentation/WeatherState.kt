package com.personal.weathering.presentation

import androidx.compose.ui.graphics.Color
import com.personal.weathering.domain.airquality.AQInfo
import com.personal.weathering.domain.models.weather.WeatherInfo
import com.personal.weathering.presentation.ui.theme.*

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