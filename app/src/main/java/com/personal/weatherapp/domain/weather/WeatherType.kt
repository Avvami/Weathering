package com.personal.weatherapp.domain.weather

import androidx.annotation.DrawableRes
import com.personal.weatherapp.R

sealed class WeatherType(
    val weatherDesc: String,
    @DrawableRes val iconRes: Int
) {
    object ClearNightSky : WeatherType(
        weatherDesc = "Clear sky",
        iconRes = R.drawable.ic_clear_night_fill1
    )
    object ClearDaySky : WeatherType(
        weatherDesc = "Clear sky",
        iconRes = R.drawable.ic_clear_day_fill1
    )
    object MainlyNightClear : WeatherType(
        weatherDesc = "Mainly clear",
        iconRes = R.drawable.ic_partly_cloudy_night_fill1
    )
    object MainlyDayClear : WeatherType(
        weatherDesc = "Mainly clear",
        iconRes = R.drawable.ic_partly_cloudy_day_fill1
    )
    object PartlyNightCloudy : WeatherType(
        weatherDesc = "Partly cloudy",
        iconRes = R.drawable.ic_partly_cloudy_night_fill1
    )
    object PartlyDayCloudy : WeatherType(
        weatherDesc = "Partly cloudy",
        iconRes = R.drawable.ic_partly_cloudy_day_fill1
    )
    object Overcast : WeatherType(
        weatherDesc = "Overcast",
        iconRes = R.drawable.ic_cloud_fill1
    )
    object Foggy : WeatherType(
        weatherDesc = "Foggy",
        iconRes = R.drawable.ic_foggy_fill1
    )
    object DepositingRimeFog : WeatherType(
        weatherDesc = "Depositing rime fog",
        iconRes = R.drawable.ic_foggy_fill1
    )
    object LightDrizzle : WeatherType(
        weatherDesc = "Light drizzle",
        iconRes = R.drawable.ic_rainy_light_fill1
    )
    object ModerateDrizzle : WeatherType(
        weatherDesc = "Moderate drizzle",
        iconRes = R.drawable.ic_rainy_light_fill1
    )
    object DenseDrizzle : WeatherType(
        weatherDesc = "Dense drizzle",
        iconRes = R.drawable.ic_rainy_heavy_fill1
    )
    object LightFreezingDrizzle : WeatherType(
        weatherDesc = "Slight freezing drizzle",
        iconRes = R.drawable.ic_rainy_snow_fill1
    )
    object DenseFreezingDrizzle : WeatherType(
        weatherDesc = "Dense freezing drizzle",
        iconRes = R.drawable.ic_rainy_snow_fill1
    )
    object SlightRain : WeatherType(
        weatherDesc = "Slight rain",
        iconRes = R.drawable.ic_rainy_fill1
    )
    object ModerateRain : WeatherType(
        weatherDesc = "Rainy",
        iconRes = R.drawable.ic_rainy_fill1
    )
    object HeavyRain : WeatherType(
        weatherDesc = "Heavy rain",
        iconRes = R.drawable.ic_rainy_heavy_fill1
    )
    object HeavyFreezingRain: WeatherType(
        weatherDesc = "Heavy freezing rain",
        iconRes = R.drawable.ic_rainy_snow_fill1
    )
    object SlightSnowFall: WeatherType(
        weatherDesc = "Slight snow fall",
        iconRes = R.drawable.ic_weather_snowy_fill1
    )
    object ModerateSnowFall: WeatherType(
        weatherDesc = "Moderate snow fall",
        iconRes = R.drawable.ic_weather_snowy_fill1
    )
    object HeavySnowFall: WeatherType(
        weatherDesc = "Heavy snow fall",
        iconRes = R.drawable.ic_snowing_heavy_fill1
    )
    object SnowGrains: WeatherType(
        weatherDesc = "Snow grains",
        iconRes = R.drawable.ic_snowing_heavy_fill1
    )
    object SlightRainShowers: WeatherType(
        weatherDesc = "Slight rain showers",
        iconRes = R.drawable.ic_rainy_heavy_fill1
    )
    object ModerateRainShowers: WeatherType(
        weatherDesc = "Moderate rain showers",
        iconRes = R.drawable.ic_rainy_heavy_fill1
    )
    object ViolentRainShowers: WeatherType(
        weatherDesc = "Violent rain showers",
        iconRes = R.drawable.ic_rainy_heavy_fill1
    )
    object SlightSnowShowers: WeatherType(
        weatherDesc = "Light snow showers",
        iconRes = R.drawable.ic_snowing_fill1
    )
    object HeavySnowShowers: WeatherType(
        weatherDesc = "Heavy snow showers",
        iconRes = R.drawable.ic_snowing_heavy_fill1
    )
    object ModerateThunderstorm: WeatherType(
        weatherDesc = "Moderate thunderstorm",
        iconRes = R.drawable.ic_thunderstorm_fill1
    )
    object SlightHailThunderstorm: WeatherType(
        weatherDesc = "Thunderstorm with slight hail",
        iconRes = R.drawable.ic_thunderstorm_fill1
    )
    object HeavyHailThunderstorm: WeatherType(
        weatherDesc = "Thunderstorm with heavy hail",
        iconRes = R.drawable.ic_thunderstorm_fill1
    )

    companion object {
        fun fromWMO(code: Int, isDay: Boolean): WeatherType {
            return when(code) {
                0 -> if (isDay) ClearDaySky else ClearNightSky
                1 -> if (isDay) MainlyDayClear else MainlyNightClear
                2 -> if (isDay) PartlyDayCloudy else PartlyNightCloudy
                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> if (isDay) ClearDaySky else ClearNightSky
            }
        }
    }
}