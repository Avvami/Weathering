package com.personal.weathering.weather.domain.models

import androidx.annotation.StringRes

data class DailyWeatherSummaryData(
    @StringRes val periodRes: Int,
    val weatherSummary: WeatherSummaryData
)
