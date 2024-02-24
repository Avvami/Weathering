package com.personal.weathering.domain.models.weather

import androidx.annotation.DrawableRes
import com.personal.weathering.R

sealed class WeatherType(
    val weatherDesc: String,
    @DrawableRes val iconSmallRes: Int,
    @DrawableRes val iconLargeRes: Int
) {
    data object ClearNightSky : WeatherType(
        weatherDesc = "Clear sky",
        iconSmallRes = R.drawable.icon_bedtime_fill1_wght400,
        iconLargeRes = R.drawable.icon_bedtime_fill0_wght200
    )
    data object ClearDaySky : WeatherType(
        weatherDesc = "Clear sky",
        iconSmallRes = R.drawable.icon_clear_day_fill1_wght400,
        iconLargeRes = R.drawable.icon_clear_day_fill0_wght200
    )
    data object MainlyNightClear : WeatherType(
        weatherDesc = "Mainly clear",
        iconSmallRes = R.drawable.icon_partly_cloudy_night_fill1_wght400,
        iconLargeRes = R.drawable.icon_partly_cloudy_night_fill0_wght200
    )
    data object MainlyDayClear : WeatherType(
        weatherDesc = "Mainly clear",
        iconSmallRes = R.drawable.icon_partly_cloudy_day_fill1_wght400,
        iconLargeRes = R.drawable.icon_partly_cloudy_day_fill0_wght200
    )
    data object PartlyNightCloudy : WeatherType(
        weatherDesc = "Partly cloudy",
        iconSmallRes = R.drawable.icon_partly_cloudy_night_fill1_wght400,
        iconLargeRes = R.drawable.icon_partly_cloudy_night_fill0_wght200
    )
    data object PartlyDayCloudy : WeatherType(
        weatherDesc = "Partly cloudy",
        iconSmallRes = R.drawable.icon_partly_cloudy_day_fill1_wght400,
        iconLargeRes = R.drawable.icon_partly_cloudy_day_fill0_wght200
    )
    data object Overcast : WeatherType(
        weatherDesc = "Overcast",
        iconSmallRes = R.drawable.icon_cloud_fill0_wght200,
        iconLargeRes = R.drawable.icon_cloud_fill1_wght400
    )
    data object Foggy : WeatherType(
        weatherDesc = "Foggy",
        iconSmallRes = R.drawable.icon_foggy_fill1_wght400,
        iconLargeRes = R.drawable.icon_foggy_fill0_wght200
    )
    data object DepositingRimeFog : WeatherType(
        weatherDesc = "Depositing rime fog",
        iconSmallRes = R.drawable.icon_foggy_fill1_wght400,
        iconLargeRes = R.drawable.icon_foggy_fill0_wght200
    )
    data object LightDrizzle : WeatherType(
        weatherDesc = "Light drizzle",
        iconSmallRes = R.drawable.icon_rainy_light_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_light_fill0_wght200
    )
    data object ModerateDrizzle : WeatherType(
        weatherDesc = "Moderate drizzle",
        iconSmallRes = R.drawable.icon_rainy_light_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_light_fill0_wght200
    )
    data object DenseDrizzle : WeatherType(
        weatherDesc = "Dense drizzle",
        iconSmallRes = R.drawable.icon_rainy_heavy_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_heavy_fill0_wght200
    )
    data object LightFreezingDrizzle : WeatherType(
        weatherDesc = "Slight freezing drizzle",
        iconSmallRes = R.drawable.icon_rainy_snow_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_snow_fill0_wght200
    )
    data object DenseFreezingDrizzle : WeatherType(
        weatherDesc = "Dense freezing drizzle",
        iconSmallRes = R.drawable.icon_rainy_snow_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_snow_fill0_wght200
    )
    data object SlightRain : WeatherType(
        weatherDesc = "Slight rain",
        iconSmallRes = R.drawable.icon_rainy_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_fill0_wght200
    )
    data object ModerateRain : WeatherType(
        weatherDesc = "Rainy",
        iconSmallRes = R.drawable.icon_rainy_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_fill0_wght200
    )
    data object HeavyRain : WeatherType(
        weatherDesc = "Heavy rain",
        iconSmallRes = R.drawable.icon_rainy_heavy_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_heavy_fill0_wght200
    )
    data object HeavyFreezingRain: WeatherType(
        weatherDesc = "Heavy freezing rain",
        iconSmallRes = R.drawable.icon_rainy_snow_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_snow_fill0_wght200
    )
    data object SlightSnowFall: WeatherType(
        weatherDesc = "Slight snow fall",
        iconSmallRes = R.drawable.icon_weather_snowy_fill1_wght400,
        iconLargeRes = R.drawable.icon_weather_snowy_fill0_wght200
    )
    data object ModerateSnowFall: WeatherType(
        weatherDesc = "Moderate snow fall",
        iconSmallRes = R.drawable.icon_weather_snowy_fill1_wght400,
        iconLargeRes = R.drawable.icon_weather_snowy_fill0_wght200
    )
    data object HeavySnowFall: WeatherType(
        weatherDesc = "Heavy snow fall",
        iconSmallRes = R.drawable.icon_snowing_heavy_fill1_wght400,
        iconLargeRes = R.drawable.icon_snowing_heavy_fill0_wght200
    )
    data object SnowGrains: WeatherType(
        weatherDesc = "Snow grains",
        iconSmallRes = R.drawable.icon_snowing_heavy_fill1_wght400,
        iconLargeRes = R.drawable.icon_snowing_heavy_fill0_wght200
    )
    data object SlightRainShowers: WeatherType(
        weatherDesc = "Slight rain showers",
        iconSmallRes = R.drawable.icon_rainy_heavy_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_heavy_fill0_wght200
    )
    data object ModerateRainShowers: WeatherType(
        weatherDesc = "Moderate rain showers",
        iconSmallRes = R.drawable.icon_rainy_heavy_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_heavy_fill0_wght200
    )
    data object ViolentRainShowers: WeatherType(
        weatherDesc = "Violent rain showers",
        iconSmallRes = R.drawable.icon_rainy_heavy_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_heavy_fill0_wght200
    )
    data object SlightSnowShowers: WeatherType(
        weatherDesc = "Light snow showers",
        iconSmallRes = R.drawable.icon_snowing_fill1_wght400,
        iconLargeRes = R.drawable.icon_snowing_fill0_wght200
    )
    data object HeavySnowShowers: WeatherType(
        weatherDesc = "Heavy snow showers",
        iconSmallRes = R.drawable.icon_snowing_heavy_fill1_wght400,
        iconLargeRes = R.drawable.icon_snowing_heavy_fill0_wght200
    )
    data object ModerateThunderstorm: WeatherType(
        weatherDesc = "Moderate thunderstorm",
        iconSmallRes = R.drawable.icon_thunderstorm_fill1_wght400,
        iconLargeRes = R.drawable.icon_thunderstorm_fill0_wght200
    )
    data object SlightHailThunderstorm: WeatherType(
        weatherDesc = "Thunderstorm with slight hail",
        iconSmallRes = R.drawable.icon_thunderstorm_fill1_wght400,
        iconLargeRes = R.drawable.icon_thunderstorm_fill0_wght200
    )
    data object HeavyHailThunderstorm: WeatherType(
        weatherDesc = "Thunderstorm with heavy hail",
        iconSmallRes = R.drawable.icon_thunderstorm_fill1_wght400,
        iconLargeRes = R.drawable.icon_thunderstorm_fill0_wght200
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