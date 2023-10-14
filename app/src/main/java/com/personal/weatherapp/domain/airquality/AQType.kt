package com.personal.weatherapp.domain.airquality

import androidx.annotation.DrawableRes
import com.personal.weatherapp.R

sealed class AQType(
    val aqDesc: String,
    @DrawableRes val iconRes: Int
) {
    object Good : AQType(
        aqDesc = "Good",
        iconRes = R.drawable.ic_eco_fill1
    )
    object Moderate : AQType(
        aqDesc = "Moderate",
        iconRes = R.drawable.ic_sentiment_neutral_fill1
    )
    object UnhealthyForSensitiveGroups : AQType(
        aqDesc = "Unhealthy for sensitive groups",
        iconRes = R.drawable.ic_ecg_heart_fill1
    )
    object Unhealthy : AQType(
        aqDesc = "Unhealthy",
        iconRes = R.drawable.ic_masks_fill1
    )
    object VeryUnhealthy : AQType(
        aqDesc = "Very unhealthy",
        iconRes = R.drawable.ic_sentiment_very_dissatisfied_fill1
    )
    object Hazardous : AQType(
        aqDesc = "Hazardous",
        iconRes = R.drawable.ic_skull_fill1
    )

    companion object {
        fun fromAQI(code: Int?): AQType {
            return when(code) {
                in 0 until 51 -> Good
                in 51 until 101 -> Moderate
                in 101 until 151 -> UnhealthyForSensitiveGroups
                in 151 until 201 -> Unhealthy
                in 201 until 301 -> VeryUnhealthy
                in 301 until 501 -> Hazardous
                else -> Good
            }
        }
    }
}