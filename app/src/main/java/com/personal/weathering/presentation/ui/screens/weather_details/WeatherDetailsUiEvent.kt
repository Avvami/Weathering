package com.personal.weathering.presentation.ui.screens.weather_details

sealed interface WeatherDetailsUiEvent {
    data class SetSelectedDayOfWeek(val dayOfWeek: Int): WeatherDetailsUiEvent
}