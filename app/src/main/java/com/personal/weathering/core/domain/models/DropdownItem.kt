package com.personal.weathering.core.domain.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DropdownItem(
    @DrawableRes val iconRes: Int? = null,
    @StringRes val textRes: Int,
    val selected: Boolean? = null,
    val onItemClick: () -> Unit
)
