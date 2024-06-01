package com.personal.weathering.weather.presenation.weather_details

sealed interface WeatherDetailsUiEvent {
    data class SetSelectedDayOfWeek(val dayOfWeek: Int): WeatherDetailsUiEvent
}