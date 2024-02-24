package com.personal.weathering.domain.models.airquality

import androidx.annotation.DrawableRes
import com.personal.weathering.R

sealed class UsAqType(
    val aqDesc: String,
    @DrawableRes val iconSmallRes: Int,
    @DrawableRes val iconLargeRes: Int
) {
    data object Good: UsAqType(
        aqDesc = "Good",
        iconSmallRes = R.drawable.icon_eco_fill1_wght400,
        iconLargeRes = R.drawable.icon_eco_fill0_wght200
    )
    data object Moderate: UsAqType(
        aqDesc = "Moderate",
        iconSmallRes = R.drawable.icon_sentiment_neutral_fill1_wght400,
        iconLargeRes = R.drawable.icon_sentiment_neutral_fill0_wght200
    )
    data object UnhealthyForSensitiveGroups: UsAqType(
        aqDesc = "Unhealthy for sensitive groups",
        iconSmallRes = R.drawable.icon_ecg_heart_fill1_wght400,
        iconLargeRes = R.drawable.icon_weather_snowy_fill0_wght200
    )
    data object Unhealthy: UsAqType(
        aqDesc = "Unhealthy",
        iconSmallRes = R.drawable.icon_masks_fill1_wght400,
        iconLargeRes = R.drawable.icon_masks_fill0_wght200
    )
    data object VeryUnhealthy: UsAqType(
        aqDesc = "Very unhealthy",
        iconSmallRes = R.drawable.icon_sentiment_very_dissatisfied_fill1_wght400,
        iconLargeRes = R.drawable.icon_sentiment_very_dissatisfied_fill0_wght200
    )
    data object Hazardous: UsAqType(
        aqDesc = "Hazardous",
        iconSmallRes = R.drawable.icon_bedtime_fill1_wght400,
        iconLargeRes = R.drawable.icon_bedtime_fill0_wght200
    )

    companion object {
        fun fromAQI(code: Int): UsAqType {
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