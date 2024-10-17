package com.personal.weathering.weather.presenation.forecast

sealed interface WeeklyForecastUiEvent {
    data class SetSelectedDayOfWeek(val dayOfWeek: Int): WeeklyForecastUiEvent
}