package com.personal.weathering.weather.presenation.forecast

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class WeeklyForecastViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var selectedDayOfWeek by mutableIntStateOf(savedStateHandle["dayOfWeek"] ?: 0)
        private set

    fun weatherDetailsUiEvent(event: WeeklyForecastUiEvent) {
        when (event) {
            is WeeklyForecastUiEvent.SetSelectedDayOfWeek -> {
                selectedDayOfWeek = event.dayOfWeek
            }
        }
    }

}