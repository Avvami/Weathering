package com.personal.weatherapp.presentation

import androidx.compose.ui.graphics.Color

sealed interface UIEvent {
    data class OpenAlertDialog(val isOpen: Boolean) : UIEvent
    object LoadWeatherInfo: UIEvent

    data class ChangeAccentColors(val surfaceColor: Color, val onSurfaceColor: Color, val plainTextColor: Color): UIEvent
}