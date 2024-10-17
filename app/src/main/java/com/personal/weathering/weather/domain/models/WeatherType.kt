package com.personal.weathering.weather.domain.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.personal.weathering.R

sealed class WeatherType(
    @StringRes val weatherDescRes: Int,
    @DrawableRes val iconFilledRes: Int,
    @DrawableRes val iconOutlinedRes: Int,
    @DrawableRes val weatherImageRes: Int
) {
    data object ClearNightSky : WeatherType(
        weatherDescRes = R.string.clear_sky,
        iconFilledRes = R.drawable.icon_bedtime_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_bedtime_fill0_wght400,
        weatherImageRes = R.drawable.clear_night_sky
    )
    data object ClearDaySky : WeatherType(
        weatherDescRes = R.string.clear_sky,
        iconFilledRes = R.drawable.icon_clear_day_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_clear_day_fill0_wght400,
        weatherImageRes = R.drawable.clear_day_sky
    )
    data object MainlyNightClear : WeatherType(
        weatherDescRes = R.string.mainly_clear,
        iconFilledRes = R.drawable.icon_partly_cloudy_night_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_partly_cloudy_night_fill0_wght400,
        weatherImageRes = R.drawable.mainly_clear_night
    )
    data object MainlyDayClear : WeatherType(
        weatherDescRes = R.string.mainly_clear,
        iconFilledRes = R.drawable.icon_partly_cloudy_day_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_partly_cloudy_day_fill0_wght400,
        weatherImageRes = R.drawable.mainly_clear_day
    )
    data object PartlyNightCloudy : WeatherType(
        weatherDescRes = R.string.partly_cloudy,
        iconFilledRes = R.drawable.icon_partly_cloudy_night_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_partly_cloudy_night_fill0_wght400,
        weatherImageRes = R.drawable.mainly_clear_night
    )
    data object PartlyDayCloudy : WeatherType(
        weatherDescRes = R.string.partly_cloudy,
        iconFilledRes = R.drawable.icon_partly_cloudy_day_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_partly_cloudy_day_fill0_wght400,
        weatherImageRes = R.drawable.mainly_clear_day
    )
    data object Overcast : WeatherType(
        weatherDescRes = R.string.overcast,
        iconFilledRes = R.drawable.icon_cloud_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_cloud_fill0_wght400,
        weatherImageRes = R.drawable.overcast
    )
    data object Foggy : WeatherType(
        weatherDescRes = R.string.foggy,
        iconFilledRes = R.drawable.icon_foggy_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_foggy_fill0_wght400,
        weatherImageRes = R.drawable.foggy
    )
    data object DepositingRimeFog : WeatherType(
        weatherDescRes = R.string.depositing_rime_fog,
        iconFilledRes = R.drawable.icon_foggy_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_foggy_fill0_wght400,
        weatherImageRes = R.drawable.rime
    )
    data object LightDrizzle : WeatherType(
        weatherDescRes = R.string.light_drizzle,
        iconFilledRes = R.drawable.icon_rainy_light_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_rainy_light_fill0_wght400,
        weatherImageRes = R.drawable.light_rain
    )
    data object ModerateDrizzle : WeatherType(
        weatherDescRes = R.string.moderate_drizzle,
        iconFilledRes = R.drawable.icon_rainy_light_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_rainy_light_fill0_wght400,
        weatherImageRes = R.drawable.light_rain
    )
    data object DenseDrizzle : WeatherType(
        weatherDescRes = R.string.dense_drizzle,
        iconFilledRes = R.drawable.icon_rainy_heavy_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_rainy_heavy_fill0_wght400,
        weatherImageRes = R.drawable.heavy_rain
    )
    data object LightFreezingDrizzle : WeatherType(
        weatherDescRes = R.string.light_freezing_drizzle,
        iconFilledRes = R.drawable.icon_rainy_snow_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_rainy_snow_fill0_wght400,
        weatherImageRes = R.drawable.light_rain
    )
    data object DenseFreezingDrizzle : WeatherType(
        weatherDescRes = R.string.dense_freezing_drizzle,
        iconFilledRes = R.drawable.icon_rainy_snow_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_rainy_snow_fill0_wght400,
        weatherImageRes = R.drawable.heavy_rain
    )
    data object SlightRain : WeatherType(
        weatherDescRes = R.string.slight_rain,
        iconFilledRes = R.drawable.icon_rainy_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_rainy_fill0_wght400,
        weatherImageRes = R.drawable.light_rain
    )
    data object ModerateRain : WeatherType(
        weatherDescRes = R.string.moderate_rain,
        iconFilledRes = R.drawable.icon_rainy_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_rainy_fill0_wght400,
        weatherImageRes = R.drawable.moderate_rain
    )
    data object HeavyRain : WeatherType(
        weatherDescRes = R.string.heavy_rain,
        iconFilledRes = R.drawable.icon_rainy_heavy_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_rainy_heavy_fill0_wght400,
        weatherImageRes = R.drawable.heavy_rain
    )
    data object HeavyFreezingRain : WeatherType(
        weatherDescRes = R.string.heavy_freezing_rain,
        iconFilledRes = R.drawable.icon_rainy_snow_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_rainy_snow_fill0_wght400,
        weatherImageRes = R.drawable.heavy_rain
    )
    data object SlightSnowFall : WeatherType(
        weatherDescRes = R.string.slight_snow_fall,
        iconFilledRes = R.drawable.icon_weather_snowy_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_weather_snowy_fill0_wght400,
        weatherImageRes = R.drawable.snowfall
    )
    data object ModerateSnowFall : WeatherType(
        weatherDescRes = R.string.moderate_snow_fall,
        iconFilledRes = R.drawable.icon_weather_snowy_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_weather_snowy_fill0_wght400,
        weatherImageRes = R.drawable.snowfall
    )
    data object HeavySnowFall : WeatherType(
        weatherDescRes = R.string.heavy_snow_fall,
        iconFilledRes = R.drawable.icon_snowing_heavy_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_snowing_heavy_fill0_wght400,
        weatherImageRes = R.drawable.heavy_snowfall
    )
    data object SnowGrains : WeatherType(
        weatherDescRes = R.string.snow_grains,
        iconFilledRes = R.drawable.icon_snowing_heavy_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_snowing_heavy_fill0_wght400,
        weatherImageRes = R.drawable.heavy_snowfall
    )
    data object SlightRainShowers : WeatherType(
        weatherDescRes = R.string.slight_rain_showers,
        iconFilledRes = R.drawable.icon_rainy_heavy_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_rainy_heavy_fill0_wght400,
        weatherImageRes = R.drawable.light_rain
    )
    data object ModerateRainShowers : WeatherType(
        weatherDescRes = R.string.moderate_rain_showers,
        iconFilledRes = R.drawable.icon_rainy_heavy_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_rainy_heavy_fill0_wght400,
        weatherImageRes = R.drawable.moderate_rain
    )
    data object ViolentRainShowers : WeatherType(
        weatherDescRes = R.string.violent_rain_showers,
        iconFilledRes = R.drawable.icon_rainy_heavy_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_rainy_heavy_fill0_wght400,
        weatherImageRes = R.drawable.heavy_rain
    )
    data object SlightSnowShowers : WeatherType(
        weatherDescRes = R.string.slight_snow_showers,
        iconFilledRes = R.drawable.icon_snowing_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_snowing_fill0_wght400,
        weatherImageRes = R.drawable.snowfall
    )
    data object HeavySnowShowers : WeatherType(
        weatherDescRes = R.string.heavy_snow_showers,
        iconFilledRes = R.drawable.icon_snowing_heavy_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_snowing_heavy_fill0_wght400,
        weatherImageRes = R.drawable.heavy_snowfall
    )
    data object ModerateThunderstorm : WeatherType(
        weatherDescRes = R.string.moderate_thunderstorm,
        iconFilledRes = R.drawable.icon_thunderstorm_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_thunderstorm_fill0_wght400,
        weatherImageRes = R.drawable.thunderstorm
    )
    data object SlightHailThunderstorm : WeatherType(
        weatherDescRes = R.string.slight_hail_thunderstorm,
        iconFilledRes = R.drawable.icon_thunderstorm_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_thunderstorm_fill0_wght400,
        weatherImageRes = R.drawable.thunderstorm
    )
    data object HeavyHailThunderstorm : WeatherType(
        weatherDescRes = R.string.heavy_hail_thunderstorm,
        iconFilledRes = R.drawable.icon_thunderstorm_fill1_wght400,
        iconOutlinedRes = R.drawable.icon_thunderstorm_fill0_wght400,
        weatherImageRes = R.drawable.heavy_thunderstorm
    )

    companion object {
        fun fromWMO(code: Int?, isDay: Boolean): WeatherType? {
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
                else -> null
            }
        }
    }
}