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
    @StringRes val aqDescRes: Int,
    @DrawableRes val iconSmallRes: Int,
    @DrawableRes val iconLargeRes: Int,
    val gradientPrimary: Color,
    val gradientSecondary: Color
) {
    data object Good: UsAqType(
        aqDescRes = R.string.good,
        iconSmallRes = R.drawable.icon_eco_fill1_wght400,
        iconLargeRes = R.drawable.icon_eco_fill0_wght200,
        gradientPrimary = aqiGoodPrimary,
        gradientSecondary = aqiGoodSecondary
    )
    data object Moderate: UsAqType(
        aqDescRes = R.string.moderate,
        iconSmallRes = R.drawable.icon_sentiment_neutral_fill1_wght400,
        iconLargeRes = R.drawable.icon_sentiment_neutral_fill0_wght200,
        gradientPrimary = aqiModeratePrimary,
        gradientSecondary = aqiModerateSecondary
    )
    data object UnhealthyForSensitiveGroups: UsAqType(
        aqDescRes = R.string.unhealthy_for_sensitive,
        iconSmallRes = R.drawable.icon_ecg_heart_fill1_wght400,
        iconLargeRes = R.drawable.icon_ecg_heart_fill0_wght200,
        gradientPrimary = aqiSensitivePrimary,
        gradientSecondary = aqiSensitiveSecondary
    )
    data object Unhealthy: UsAqType(
        aqDescRes = R.string.unhealthy,
        iconSmallRes = R.drawable.icon_masks_fill1_wght400,
        iconLargeRes = R.drawable.icon_masks_fill0_wght200,
        gradientPrimary = aqiUnhealthyPrimary,
        gradientSecondary = aqiUnhealthySecondary
    )
    data object VeryUnhealthy: UsAqType(
        aqDescRes = R.string.very_unhealthy,
        iconSmallRes = R.drawable.icon_sentiment_very_dissatisfied_fill1_wght400,
        iconLargeRes = R.drawable.icon_sentiment_very_dissatisfied_fill0_wght200,
        gradientPrimary = aqiVeryUnhealthyPrimary,
        gradientSecondary = aqiVeryUnhealthySecondary
    )
    data object Hazardous: UsAqType(
        aqDescRes = R.string.hazardous,
        iconSmallRes = R.drawable.icon_skull_fill1_wght400,
        iconLargeRes = R.drawable.icon_skull_fill0_wght200,
        gradientPrimary = aqiHazardousPrimary,
        gradientSecondary = aqiHazardousSecondary
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