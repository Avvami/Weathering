package com.personal.weathering.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.personal.weathering.R

sealed interface UiEvent {
    data class LoadWeatherInfo(val lat: Double, val lon: Double): UiEvent
    data class ShowMessageDialog(
        @DrawableRes val iconRes: Int? = null,
        @StringRes val titleRes: Int? = null,
        @StringRes val messageRes: Int? = null,
        val messageString: String? = null,
        @StringRes val dismissTextRes: Int? = null,
        val onDismiss: (() -> Unit)? = null,
        @StringRes val confirmTextRes: Int = R.string.ok,
        val onConfirm: (() -> Unit)? = null
    ): UiEvent
    data object CloseMessageDialog: UiEvent
    data class SetCurrentCityState(val cityId: Int, val city: String, val lat: Double, val lon: Double): UiEvent
    data class SetSearchLanguage(val code: String): UiEvent
    data class SetTemperatureUnit(val useCelsius: Boolean): UiEvent
    data class SetSpeedUnit(val useKmPerHour: Boolean): UiEvent
    data class SetPressureUnit(val useHpa: Boolean): UiEvent
    data class SetAqiUnit(val useUSaq: Boolean): UiEvent
    data class UpdateAqInfo(val lat: Double, val lon: Double): UiEvent
    data class AddFavorite(val cityId: Int, val city: String, val lat: Double, val lon: Double): UiEvent
    data class RemoveFavorite(val cityId: Int, val city: String, val lat: Double, val lon: Double): UiEvent
}