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

sealed class EuAqType(
    val aqValue: Int,
    @StringRes val aqIndexRes: Int,
    @StringRes val aqDescRes: Int,
    @DrawableRes val iconSmallRes: Int,
    @DrawableRes val iconLargeRes: Int,
    val gradientPrimary: Color,
    val gradientSecondary: Color
) {
    data class Good(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.good,
        aqDescRes = R.string.eu_good_aqi_general_description,
        iconSmallRes = R.drawable.icon_eco_fill1_wght400,
        iconLargeRes = R.drawable.icon_eco_fill0_wght200,
        gradientPrimary = aqiGoodPrimary,
        gradientSecondary = aqiGoodSecondary
    )
    data class Fair(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.fair,
        aqDescRes = R.string.eu_fair_aqi_general_description,
        iconSmallRes = R.drawable.icon_sentiment_neutral_fill1_wght400,
        iconLargeRes = R.drawable.icon_sentiment_neutral_fill0_wght200,
        gradientPrimary = aqiModeratePrimary,
        gradientSecondary = aqiModerateSecondary
    )
    data class Moderate(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.moderate,
        aqDescRes = R.string.eu_moderate_aqi_general_description,
        iconSmallRes = R.drawable.icon_ecg_heart_fill1_wght400,
        iconLargeRes = R.drawable.icon_ecg_heart_fill0_wght200,
        gradientPrimary = aqiSensitivePrimary,
        gradientSecondary = aqiSensitiveSecondary
    )
    data class Poor(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.poor,
        aqDescRes = R.string.eu_poor_aqi_general_description,
        iconSmallRes = R.drawable.icon_masks_fill1_wght400,
        iconLargeRes = R.drawable.icon_masks_fill0_wght200,
        gradientPrimary = aqiUnhealthyPrimary,
        gradientSecondary = aqiUnhealthySecondary
    )
    data class VeryPoor(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.very_poor,
        aqDescRes = R.string.eu_very_poor_aqi_general_description,
        iconSmallRes = R.drawable.icon_sentiment_very_dissatisfied_fill1_wght400,
        iconLargeRes = R.drawable.icon_sentiment_very_dissatisfied_fill0_wght200,
        gradientPrimary = aqiVeryUnhealthyPrimary,
        gradientSecondary = aqiVeryUnhealthySecondary
    )
    data class ExtremelyPoor(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.extremely_poor,
        aqDescRes = R.string.eu_extremely_poor_aqi_general_description,
        iconSmallRes = R.drawable.icon_skull_fill1_wght400,
        iconLargeRes = R.drawable.icon_skull_fill0_wght200,
        gradientPrimary = aqiHazardousPrimary,
        gradientSecondary = aqiHazardousSecondary
    )

    companion object {
        fun fromAQI(value: Int): EuAqType {
            return when(value) {
                in 0 until 21 -> Good(value)
                in 21 until 41 -> Fair(value)
                in 41 until 61 -> Moderate(value)
                in 61 until 81 -> Poor(value)
                in 81 until 101 -> VeryPoor(value)
                else -> ExtremelyPoor(value)
            }
        }
    }
}