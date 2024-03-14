package com.personal.weathering.presentation.ui.screens.weather_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class WeatherDetailsViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var selectedDayOfWeek by mutableIntStateOf(savedStateHandle["dayOfWeek"] ?: 0)
        private set

    fun weatherDetailsUiEvent(event: WeatherDetailsUiEvent) {
        when (event) {
            is WeatherDetailsUiEvent.SetSelectedDayOfWeek -> {
                selectedDayOfWeek = event.dayOfWeek
            }
        }
    }

}