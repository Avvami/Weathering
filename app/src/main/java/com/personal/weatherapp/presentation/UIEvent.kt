package com.personal.weatherapp.presentation

sealed interface UIEvent {
    data class OpenAlertDialog(val isOpen: Boolean) : UIEvent
    object LoadWeatherInfo: UIEvent
}