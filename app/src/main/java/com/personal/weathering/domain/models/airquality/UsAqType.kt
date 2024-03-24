package com.personal.weathering.domain.models.airquality

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.personal.weathering.R
import com.personal.weathering.presentation.ui.theme.aqiGoodPrimary
import com.personal.weathering.presentation.ui.theme.aqiGoodSecondary
import com.personal.weathering.presentation.ui.theme.aqiHazardousPrimary
import com.personal.weathering.presentation.ui.theme.aqiHazardousSecondary
import com.personal.weathering.presentation.ui.theme.aqiModeratePrimary
import com.personal.weathering.presentation.ui.theme.aqiModerateSecondary
import com.personal.weathering.presentation.ui.theme.aqiSensitivePrimary
import com.personal.weathering.presentation.ui.theme.aqiSensitiveSecondary
import com.personal.weathering.presentation.ui.theme.aqiUnhealthyPrimary
import com.personal.weathering.presentation.ui.theme.aqiUnhealthySecondary
import com.personal.weathering.presentation.ui.theme.aqiVeryUnhealthyPrimary
import com.personal.weathering.presentation.ui.theme.aqiVeryUnhealthySecondary

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
        gradientPrimary = aqiGoodPrimary,
        gradientSecondary = aqiGoodSecondary
    )
    data class Moderate(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.moderate,
        aqDescRes = R.string.us_moderate_aqi_description,
        iconSmallRes = R.drawable.icon_sentiment_neutral_fill1_wght400,
        iconLargeRes = R.drawable.icon_sentiment_neutral_fill0_wght200,
        gradientPrimary = aqiModeratePrimary,
        gradientSecondary = aqiModerateSecondary
    )
    data class UnhealthyForSensitiveGroups(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.unhealthy_for_sensitive,
        aqDescRes = R.string.us_unhealthy_sensitive_groups_aqi_description,
        iconSmallRes = R.drawable.icon_ecg_heart_fill1_wght400,
        iconLargeRes = R.drawable.icon_ecg_heart_fill0_wght200,
        gradientPrimary = aqiSensitivePrimary,
        gradientSecondary = aqiSensitiveSecondary
    )
    data class Unhealthy(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.unhealthy,
        aqDescRes = R.string.us_unhealthy_aqi_description,
        iconSmallRes = R.drawable.icon_masks_fill1_wght400,
        iconLargeRes = R.drawable.icon_masks_fill0_wght200,
        gradientPrimary = aqiUnhealthyPrimary,
        gradientSecondary = aqiUnhealthySecondary
    )
    data class VeryUnhealthy(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.very_unhealthy,
        aqDescRes = R.string.us_very_unhealthy_aqi_description,
        iconSmallRes = R.drawable.icon_sentiment_very_dissatisfied_fill1_wght400,
        iconLargeRes = R.drawable.icon_sentiment_very_dissatisfied_fill0_wght200,
        gradientPrimary = aqiVeryUnhealthyPrimary,
        gradientSecondary = aqiVeryUnhealthySecondary
    )
    data class Hazardous(val value: Int): UsAqType(
        aqValue = value,
        aqIndexRes = R.string.hazardous,
        aqDescRes = R.string.us_hazardous_aqi_description,
        iconSmallRes = R.drawable.icon_skull_fill1_wght400,
        iconLargeRes = R.drawable.icon_skull_fill0_wght200,
        gradientPrimary = aqiHazardousPrimary,
        gradientSecondary = aqiHazardousSecondary
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