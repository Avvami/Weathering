package com.personal.weathering.aq.domain.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.personal.weathering.R
import com.personal.weathering.ui.theme.aqiEuExtremelyPoorUsHazardousPrimary
import com.personal.weathering.ui.theme.aqiEuExtremelyPoorUsHazardousSecondary
import com.personal.weathering.ui.theme.aqiEuFairUsGoodPrimary
import com.personal.weathering.ui.theme.aqiEuFairUsGoodSecondary
import com.personal.weathering.ui.theme.aqiEuGoodPrimary
import com.personal.weathering.ui.theme.aqiEuGoodSecondary
import com.personal.weathering.ui.theme.aqiEuPoorUsUnhealthyPrimary
import com.personal.weathering.ui.theme.aqiEuPoorUsUnhealthySecondary
import com.personal.weathering.ui.theme.aqiEuUsModeratePrimary
import com.personal.weathering.ui.theme.aqiEuUsModerateSecondary
import com.personal.weathering.ui.theme.aqiEuVeryPoorUsVeryUnhealthyPrimary
import com.personal.weathering.ui.theme.aqiEuVeryPoorUsVeryUnhealthySecondary

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
        gradientPrimary = aqiEuGoodPrimary,
        gradientSecondary = aqiEuGoodSecondary
    )
    data class Fair(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.fair,
        aqDescRes = R.string.eu_fair_aqi_general_description,
        iconSmallRes = R.drawable.icon_sentiment_neutral_fill1_wght400,
        iconLargeRes = R.drawable.icon_sentiment_neutral_fill0_wght200,
        gradientPrimary = aqiEuFairUsGoodPrimary,
        gradientSecondary = aqiEuFairUsGoodSecondary
    )
    data class Moderate(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.moderate,
        aqDescRes = R.string.eu_moderate_aqi_general_description,
        iconSmallRes = R.drawable.icon_ecg_heart_fill1_wght400,
        iconLargeRes = R.drawable.icon_ecg_heart_fill0_wght200,
        gradientPrimary = aqiEuUsModeratePrimary,
        gradientSecondary = aqiEuUsModerateSecondary
    )
    data class Poor(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.poor,
        aqDescRes = R.string.eu_poor_aqi_general_description,
        iconSmallRes = R.drawable.icon_masks_fill1_wght400,
        iconLargeRes = R.drawable.icon_masks_fill0_wght200,
        gradientPrimary = aqiEuPoorUsUnhealthyPrimary,
        gradientSecondary = aqiEuPoorUsUnhealthySecondary
    )
    data class VeryPoor(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.very_poor,
        aqDescRes = R.string.eu_very_poor_aqi_general_description,
        iconSmallRes = R.drawable.icon_sentiment_very_dissatisfied_fill1_wght400,
        iconLargeRes = R.drawable.icon_sentiment_very_dissatisfied_fill0_wght200,
        gradientPrimary = aqiEuVeryPoorUsVeryUnhealthyPrimary,
        gradientSecondary = aqiEuVeryPoorUsVeryUnhealthySecondary
    )
    data class ExtremelyPoor(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.extremely_poor,
        aqDescRes = R.string.eu_extremely_poor_aqi_general_description,
        iconSmallRes = R.drawable.icon_skull_fill1_wght400,
        iconLargeRes = R.drawable.icon_skull_fill0_wght200,
        gradientPrimary = aqiEuExtremelyPoorUsHazardousPrimary,
        gradientSecondary = aqiEuExtremelyPoorUsHazardousSecondary
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