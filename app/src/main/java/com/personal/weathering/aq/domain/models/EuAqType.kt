package com.personal.weathering.aq.domain.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.personal.weathering.R

sealed class EuAqType(
    val aqValue: Int,
    @StringRes val aqIndexRes: Int,
    @StringRes val aqDescRes: Int,
    @DrawableRes val iconFilledRes: Int,
    @DrawableRes val iconOutlinedRes: Int
) {
    data class Good(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.good,
        aqDescRes = R.string.eu_good_aqi_general_description,
        iconFilledRes = R.drawable.icon_eco_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_eco_fill0_wght400
    )
    data class Fair(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.fair,
        aqDescRes = R.string.eu_fair_aqi_general_description,
        iconFilledRes = R.drawable.icon_sentiment_neutral_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_sentiment_neutral_fill0_wght400
    )
    data class Moderate(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.moderate,
        aqDescRes = R.string.eu_moderate_aqi_general_description,
        iconFilledRes = R.drawable.icon_ecg_heart_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_ecg_heart_fill0_wght400
    )
    data class Poor(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.poor,
        aqDescRes = R.string.eu_poor_aqi_general_description,
        iconFilledRes = R.drawable.icon_masks_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_masks_fill0_wght400
    )
    data class VeryPoor(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.very_poor,
        aqDescRes = R.string.eu_very_poor_aqi_general_description,
        iconFilledRes = R.drawable.icon_sentiment_very_dissatisfied_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_sentiment_very_dissatisfied_fill0_wght400
    )
    data class ExtremelyPoor(val value: Int): EuAqType(
        aqValue = value,
        aqIndexRes = R.string.extremely_poor,
        aqDescRes = R.string.eu_extremely_poor_aqi_general_description,
        iconFilledRes = R.drawable.icon_skull_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_skull_fill0_wght400
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