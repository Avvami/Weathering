package com.personal.weathering.aq.domain.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.personal.weathering.R

sealed class UsAqType(
    val aqValue: Int,
    @StringRes val aqIndexRes: Int,
    @StringRes val aqDescRes: Int,
    @DrawableRes val iconFilledRes: Int,
    @DrawableRes val iconOutlinedRes: Int
) {
    data class Good(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.good,
        aqDescRes = R.string.us_good_aqi_description,
        iconFilledRes = R.drawable.icon_eco_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_eco_fill0_wght400
    )
    data class Moderate(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.moderate,
        aqDescRes = R.string.us_moderate_aqi_description,
        iconFilledRes = R.drawable.icon_sentiment_neutral_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_sentiment_neutral_fill0_wght400
    )
    data class UnhealthyForSensitiveGroups(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.unhealthy_for_sensitive,
        aqDescRes = R.string.us_unhealthy_sensitive_groups_aqi_description,
        iconFilledRes = R.drawable.icon_ecg_heart_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_ecg_heart_fill0_wght400
    )
    data class Unhealthy(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.unhealthy,
        aqDescRes = R.string.us_unhealthy_aqi_description,
        iconFilledRes = R.drawable.icon_masks_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_masks_fill0_wght400
    )
    data class VeryUnhealthy(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.very_unhealthy,
        aqDescRes = R.string.us_very_unhealthy_aqi_description,
        iconFilledRes = R.drawable.icon_sentiment_very_dissatisfied_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_sentiment_very_dissatisfied_fill0_wght400
    )
    data class Hazardous(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.hazardous,
        aqDescRes = R.string.us_hazardous_aqi_description,
        iconFilledRes = R.drawable.icon_skull_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_skull_fill0_wght400
    )

    companion object {
        fun fromAQI(value: Int): UsAqType {
            return when(value) {
                in 0 until 51 -> Good(value)
                in 51 until 101 -> Moderate(value)
                in 101 until 151 -> UnhealthyForSensitiveGroups(value)
                in 151 until 201 -> Unhealthy(value)
                in 201 until 301 -> VeryUnhealthy(value)
                else -> Hazardous(value)
            }
        }
    }
}