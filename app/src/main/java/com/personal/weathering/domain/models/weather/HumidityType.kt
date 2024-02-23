package com.personal.weathering.domain.models.weather

import androidx.annotation.DrawableRes
import com.personal.weathering.R

sealed class HumidityType(
    val iconDesc: String,
    @DrawableRes val iconRes: Int
) {
    data object Low: HumidityType(
        iconDesc = "Humidity low",
        iconRes = R.drawable.icon_humidity_low_fill1_wght400
    )
    data object Middle: HumidityType(
        iconDesc = "Humidity middle",
        iconRes = R.drawable.ic_humidity_mid_fill1
    )
    data object High: HumidityType(
        iconDesc = "Humidity high",
        iconRes = R.drawable.ic_humidity_high_fill1
    )

    companion object {
        fun fromPercentage(humidity: Int): HumidityType {
            return when(humidity) {
                in 0 until 21 -> Low
                in 21 until 81 -> Middle
                in 81 until 101-> High
                else -> Middle
            }
        }
    }
}
