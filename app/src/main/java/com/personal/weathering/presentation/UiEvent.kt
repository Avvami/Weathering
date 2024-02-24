package com.personal.weathering.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.personal.weathering.R

sealed interface UiEvent {
    data object LoadWeatherInfo: UiEvent
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
}