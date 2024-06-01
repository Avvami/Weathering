package com.personal.weathering.aq.domain.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.personal.weathering.R
import com.personal.weathering.ui.theme.aqiEuExtremelyPoorUsHazardousPrimary
import com.personal.weathering.ui.theme.aqiEuExtremelyPoorUsHazardousSecondary
import com.personal.weathering.ui.theme.aqiEuFairUsGoodPrimary
import com.personal.weathering.ui.theme.aqiEuFairUsGoodSecondary
import com.personal.weathering.ui.theme.aqiEuPoorUsUnhealthyPrimary
import com.personal.weathering.ui.theme.aqiEuPoorUsUnhealthySecondary
import com.personal.weathering.ui.theme.aqiEuUsModeratePrimary
import com.personal.weathering.ui.theme.aqiEuUsModerateSecondary
import com.personal.weathering.ui.theme.aqiEuVeryPoorUsVeryUnhealthyPrimary
import com.personal.weathering.ui.theme.aqiEuVeryPoorUsVeryUnhealthySecondary
import com.personal.weathering.ui.theme.aqiUsSensitivePrimary
import com.personal.weathering.ui.theme.aqiUsSensitiveSecondary

sealed class UsAqType(
    val aqValue: Int,
    @StringRes val aqIndexRes: Int,
    @StringRes val aqDescRes: Int,
    @DrawableRes val iconSmallRes: Int,
    @DrawableRes val iconLargeRes: Int,
    val gradientPrimary: Color,
    val gradientSecondary: Color
) {
    data class Good(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.good,
        aqDescRes = R.string.us_good_aqi_description,
        iconSmallRes = R.drawable.icon_eco_fill1_wght400,
        iconLargeRes = R.drawable.icon_eco_fill0_wght200,
        gradientPrimary = aqiEuFairUsGoodPrimary,
        gradientSecondary = aqiEuFairUsGoodSecondary
    )
    data class Moderate(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.moderate,
        aqDescRes = R.string.us_moderate_aqi_description,
        iconSmallRes = R.drawable.icon_sentiment_neutral_fill1_wght400,
        iconLargeRes = R.drawable.icon_sentiment_neutral_fill0_wght200,
        gradientPrimary = aqiEuUsModeratePrimary,
        gradientSecondary = aqiEuUsModerateSecondary
    )
    data class UnhealthyForSensitiveGroups(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.unhealthy_for_sensitive,
        aqDescRes = R.string.us_unhealthy_sensitive_groups_aqi_description,
        iconSmallRes = R.drawable.icon_ecg_heart_fill1_wght400,
        iconLargeRes = R.drawable.icon_ecg_heart_fill0_wght200,
        gradientPrimary = aqiUsSensitivePrimary,
        gradientSecondary = aqiUsSensitiveSecondary
    )
    data class Unhealthy(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.unhealthy,
        aqDescRes = R.string.us_unhealthy_aqi_description,
        iconSmallRes = R.drawable.icon_masks_fill1_wght400,
        iconLargeRes = R.drawable.icon_masks_fill0_wght200,
        gradientPrimary = aqiEuPoorUsUnhealthyPrimary,
        gradientSecondary = aqiEuPoorUsUnhealthySecondary
    )
    data class VeryUnhealthy(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.very_unhealthy,
        aqDescRes = R.string.us_very_unhealthy_aqi_description,
        iconSmallRes = R.drawable.icon_sentiment_very_dissatisfied_fill1_wght400,
        iconLargeRes = R.drawable.icon_sentiment_very_dissatisfied_fill0_wght200,
        gradientPrimary = aqiEuVeryPoorUsVeryUnhealthyPrimary,
        gradientSecondary = aqiEuVeryPoorUsVeryUnhealthySecondary
    )
    data class Hazardous(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.hazardous,
        aqDescRes = R.string.us_hazardous_aqi_description,
        iconSmallRes = R.drawable.icon_skull_fill1_wght400,
        iconLargeRes = R.drawable.icon_skull_fill0_wght200,
        gradientPrimary = aqiEuExtremelyPoorUsHazardousPrimary,
        gradientSecondary = aqiEuExtremelyPoorUsHazardousSecondary
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