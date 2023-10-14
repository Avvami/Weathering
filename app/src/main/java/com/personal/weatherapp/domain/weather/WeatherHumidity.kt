package com.personal.weatherapp.domain.weather

import androidx.annotation.DrawableRes
import com.personal.weatherapp.R

sealed class WeatherHumidity(
    val humidityDesc: String,
    @DrawableRes val iconRes: Int
) {
    object Low : WeatherHumidity(
        humidityDesc = "Humidity low",
        iconRes = R.drawable.ic_humidity_low_fill1
    )
    object Middle : WeatherHumidity(
        humidityDesc = "Humidity middle",
        iconRes = R.drawable.ic_humidity_mid_fill1
    )
    object High : WeatherHumidity(
        humidityDesc = "Humidity high",
        iconRes = R.drawable.ic_humidity_high_fill1
    )

    companion object {
        fun fromPercentage(humidity: Int): WeatherHumidity {
            return when(humidity) {
                in 0 until 21 -> Low
                in 21 until 81 -> Middle
                in 81 until 101-> High
                else -> Middle
            }
        }
    }
}
