package com.personal.weathering.core.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.personal.weathering.R

data class MessageDialogState(
    val isShown: Boolean = false,
    @DrawableRes val iconRes: Int? = null,
    val content: @Composable (() -> Unit)? = null,
    @StringRes val titleRes: Int? = null,
    @StringRes val messageRes: Int? = null,
    val messageString: String? = null,
    val onDismissRequest: () -> Unit = {},
    @StringRes val dismissTextRes: Int? = null,
    val onDismiss: (() -> Unit)? = null,
    @StringRes val confirmTextRes: Int = R.string.ok,
    val onConfirm: () -> Unit = {}
)
