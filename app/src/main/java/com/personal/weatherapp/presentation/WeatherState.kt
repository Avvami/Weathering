package com.personal.weatherapp.presentation

import com.personal.weatherapp.domain.airquality.AQInfo
import com.personal.weatherapp.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val aqInfo: AQInfo? = null,
    val isLoading: Boolean = false,
    val weatherError: String? = null,
    val aqError: String? = null,
    val openAlertDialog: Boolean = false
)