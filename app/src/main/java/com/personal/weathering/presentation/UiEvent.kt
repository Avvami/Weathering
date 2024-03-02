package com.personal.weathering.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.personal.weathering.R
import com.personal.weathering.presentation.state.CurrentCityState

sealed interface UiEvent {
    data class LoadWeatherInfo(val lat: Double = CurrentCityState().lat, val lon: Double = CurrentCityState().lon): UiEvent
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
    data class UpdateCurrentCityState(val city: String, val lat: Double, val lon: Double): UiEvent
}