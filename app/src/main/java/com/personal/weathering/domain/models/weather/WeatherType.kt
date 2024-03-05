package com.personal.weathering.domain.models.weather

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.personal.weathering.R
import com.personal.weathering.presentation.ui.theme.clearDaySkyPrimary
import com.personal.weathering.presentation.ui.theme.clearDaySkySecondary
import com.personal.weathering.presentation.ui.theme.clearNightSkyPrimary
import com.personal.weathering.presentation.ui.theme.clearNightSkySecondary
import com.personal.weathering.presentation.ui.theme.drizzlePrimary
import com.personal.weathering.presentation.ui.theme.drizzleSecondary
import com.personal.weathering.presentation.ui.theme.fogPrimary
import com.personal.weathering.presentation.ui.theme.fogSecondary
import com.personal.weathering.presentation.ui.theme.freezingDrizzlePrimary
import com.personal.weathering.presentation.ui.theme.freezingDrizzleSecondary
import com.personal.weathering.presentation.ui.theme.freezingRainPrimary
import com.personal.weathering.presentation.ui.theme.freezingRainSecondary
import com.personal.weathering.presentation.ui.theme.partlyCloudyPrimary
import com.personal.weathering.presentation.ui.theme.partlyCloudySecondary
import com.personal.weathering.presentation.ui.theme.rainPrimary
import com.personal.weathering.presentation.ui.theme.rainSecondary
import com.personal.weathering.presentation.ui.theme.rainShowerPrimary
import com.personal.weathering.presentation.ui.theme.rainShowerSecondary
import com.personal.weathering.presentation.ui.theme.snowFallPrimary
import com.personal.weathering.presentation.ui.theme.snowFallSecondary
import com.personal.weathering.presentation.ui.theme.snowGrainsPrimary
import com.personal.weathering.presentation.ui.theme.snowGrainsSecondary
import com.personal.weathering.presentation.ui.theme.snowShowerPrimary
import com.personal.weathering.presentation.ui.theme.snowShowerSecondary
import com.personal.weathering.presentation.ui.theme.thunderstormPrimary
import com.personal.weathering.presentation.ui.theme.thunderstormSecondary
import com.personal.weathering.presentation.ui.theme.thunderstormWithHailPrimary
import com.personal.weathering.presentation.ui.theme.thunderstormWithHailSecondary

sealed class WeatherType(
    @StringRes val weatherDescRes: Int,
    @DrawableRes val iconSmallRes: Int,
    @DrawableRes val iconLargeRes: Int,
    val gradientPrimary: Color,
    val gradientSecondary: Color
) {
    data object ClearNightSky : WeatherType(
        weatherDescRes = R.string.clear_night_sky,
        iconSmallRes = R.drawable.icon_bedtime_fill1_wght400,
        iconLargeRes = R.drawable.icon_bedtime_fill0_wght200,
        gradientPrimary = clearNightSkyPrimary,
        gradientSecondary = clearNightSkySecondary
    )
    data object ClearDaySky : WeatherType(
        weatherDescRes = R.string.clear_day_sky,
        iconSmallRes = R.drawable.icon_clear_day_fill1_wght400,
        iconLargeRes = R.drawable.icon_clear_day_fill0_wght200,
        gradientPrimary = clearDaySkyPrimary,
        gradientSecondary = clearDaySkySecondary
    )
    data object MainlyNightClear : WeatherType(
        weatherDescRes = R.string.mainly_night_clear,
        iconSmallRes = R.drawable.icon_partly_cloudy_night_fill1_wght400,
        iconLargeRes = R.drawable.icon_partly_cloudy_night_fill0_wght200,
        gradientPrimary = clearNightSkyPrimary,
        gradientSecondary = clearNightSkySecondary
    )
    data object MainlyDayClear : WeatherType(
        weatherDescRes = R.string.mainly_day_clear,
        iconSmallRes = R.drawable.icon_partly_cloudy_day_fill1_wght400,
        iconLargeRes = R.drawable.icon_partly_cloudy_day_fill0_wght200,
        gradientPrimary = partlyCloudyPrimary,
        gradientSecondary = partlyCloudySecondary
    )
    data object PartlyNightCloudy : WeatherType(
        weatherDescRes = R.string.partly_night_cloudy,
        iconSmallRes = R.drawable.icon_partly_cloudy_night_fill1_wght400,
        iconLargeRes = R.drawable.icon_partly_cloudy_night_fill0_wght200,
        gradientPrimary = clearNightSkyPrimary,
        gradientSecondary = clearNightSkySecondary
    )
    data object PartlyDayCloudy : WeatherType(
        weatherDescRes = R.string.partly_day_cloudy,
        iconSmallRes = R.drawable.icon_partly_cloudy_day_fill1_wght400,
        iconLargeRes = R.drawable.icon_partly_cloudy_day_fill0_wght200,
        gradientPrimary = partlyCloudyPrimary,
        gradientSecondary = partlyCloudySecondary
    )
    data object Overcast : WeatherType(
        weatherDescRes = R.string.overcast,
        iconSmallRes = R.drawable.icon_cloud_fill1_wght400,
        iconLargeRes = R.drawable.icon_cloud_fill0_wght200,
        gradientPrimary = fogPrimary,
        gradientSecondary = fogSecondary
    )
    data object Foggy : WeatherType(
        weatherDescRes = R.string.foggy,
        iconSmallRes = R.drawable.icon_foggy_fill1_wght400,
        iconLargeRes = R.drawable.icon_foggy_fill0_wght200,
        gradientPrimary = fogPrimary,
        gradientSecondary = fogSecondary
    )
    data object DepositingRimeFog : WeatherType(
        weatherDescRes = R.string.depositing_rime_fog,
        iconSmallRes = R.drawable.icon_foggy_fill1_wght400,
        iconLargeRes = R.drawable.icon_foggy_fill0_wght200,
        gradientPrimary = fogPrimary,
        gradientSecondary = fogSecondary
    )
    data object LightDrizzle : WeatherType(
        weatherDescRes = R.string.light_drizzle,
        iconSmallRes = R.drawable.icon_rainy_light_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_light_fill0_wght200,
        gradientPrimary = drizzlePrimary,
        gradientSecondary = drizzleSecondary
    )
    data object ModerateDrizzle : WeatherType(
        weatherDescRes = R.string.moderate_drizzle,
        iconSmallRes = R.drawable.icon_rainy_light_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_light_fill0_wght200,
        gradientPrimary = drizzlePrimary,
        gradientSecondary = drizzleSecondary
    )
    data object DenseDrizzle : WeatherType(
        weatherDescRes = R.string.dense_drizzle,
        iconSmallRes = R.drawable.icon_rainy_heavy_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_heavy_fill0_wght200,
        gradientPrimary = drizzlePrimary,
        gradientSecondary = drizzleSecondary
    )
    data object LightFreezingDrizzle : WeatherType(
        weatherDescRes = R.string.light_freezing_drizzle,
        iconSmallRes = R.drawable.icon_rainy_snow_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_snow_fill0_wght200,
        gradientPrimary = freezingDrizzlePrimary,
        gradientSecondary = freezingDrizzleSecondary
    )
    data object DenseFreezingDrizzle : WeatherType(
        weatherDescRes = R.string.dense_freezing_drizzle,
        iconSmallRes = R.drawable.icon_rainy_snow_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_snow_fill0_wght200,
        gradientPrimary = freezingDrizzlePrimary,
        gradientSecondary = freezingDrizzleSecondary
    )
    data object SlightRain : WeatherType(
        weatherDescRes = R.string.slight_rain,
        iconSmallRes = R.drawable.icon_rainy_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_fill0_wght200,
        gradientPrimary = rainPrimary,
        gradientSecondary = rainSecondary
    )
    data object ModerateRain : WeatherType(
        weatherDescRes = R.string.moderate_rain,
        iconSmallRes = R.drawable.icon_rainy_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_fill0_wght200,
        gradientPrimary = rainPrimary,
        gradientSecondary = rainSecondary
    )
    data object HeavyRain : WeatherType(
        weatherDescRes = R.string.heavy_rain,
        iconSmallRes = R.drawable.icon_rainy_heavy_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_heavy_fill0_wght200,
        gradientPrimary = rainPrimary,
        gradientSecondary = rainSecondary
    )
    data object HeavyFreezingRain : WeatherType(
        weatherDescRes = R.string.heavy_freezing_rain,
        iconSmallRes = R.drawable.icon_rainy_snow_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_snow_fill0_wght200,
        gradientPrimary = freezingRainPrimary,
        gradientSecondary = freezingRainSecondary
    )
    data object SlightSnowFall : WeatherType(
        weatherDescRes = R.string.slight_snow_fall,
        iconSmallRes = R.drawable.icon_weather_snowy_fill1_wght400,
        iconLargeRes = R.drawable.icon_weather_snowy_fill0_wght200,
        gradientPrimary = snowFallPrimary,
        gradientSecondary = snowFallSecondary
    )
    data object ModerateSnowFall : WeatherType(
        weatherDescRes = R.string.moderate_snow_fall,
        iconSmallRes = R.drawable.icon_weather_snowy_fill1_wght400,
        iconLargeRes = R.drawable.icon_weather_snowy_fill0_wght200,
        gradientPrimary = snowFallPrimary,
        gradientSecondary = snowFallSecondary
    )
    data object HeavySnowFall : WeatherType(
        weatherDescRes = R.string.heavy_snow_fall,
        iconSmallRes = R.drawable.icon_snowing_heavy_fill1_wght400,
        iconLargeRes = R.drawable.icon_snowing_heavy_fill0_wght200,
        gradientPrimary = snowFallPrimary,
        gradientSecondary = snowFallSecondary
    )
    data object SnowGrains : WeatherType(
        weatherDescRes = R.string.snow_grains,
        iconSmallRes = R.drawable.icon_snowing_heavy_fill1_wght400,
        iconLargeRes = R.drawable.icon_snowing_heavy_fill0_wght200,
        gradientPrimary = snowGrainsPrimary,
        gradientSecondary = snowGrainsSecondary
    )
    data object SlightRainShowers : WeatherType(
        weatherDescRes = R.string.slight_rain_showers,
        iconSmallRes = R.drawable.icon_rainy_heavy_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_heavy_fill0_wght200,
        gradientPrimary = rainShowerPrimary,
        gradientSecondary = rainShowerSecondary
    )
    data object ModerateRainShowers : WeatherType(
        weatherDescRes = R.string.moderate_rain_showers,
        iconSmallRes = R.drawable.icon_rainy_heavy_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_heavy_fill0_wght200,
        gradientPrimary = rainShowerPrimary,
        gradientSecondary = rainShowerSecondary
    )
    data object ViolentRainShowers : WeatherType(
        weatherDescRes = R.string.violent_rain_showers,
        iconSmallRes = R.drawable.icon_rainy_heavy_fill1_wght400,
        iconLargeRes = R.drawable.icon_rainy_heavy_fill0_wght200,
        gradientPrimary = rainShowerPrimary,
        gradientSecondary = rainShowerSecondary
    )
    data object SlightSnowShowers : WeatherType(
        weatherDescRes = R.string.slight_snow_showers,
        iconSmallRes = R.drawable.icon_snowing_fill1_wght400,
        iconLargeRes = R.drawable.icon_snowing_fill0_wght200,
        gradientPrimary = snowShowerPrimary,
        gradientSecondary = snowShowerSecondary
    )
    data object HeavySnowShowers : WeatherType(
        weatherDescRes = R.string.heavy_snow_showers,
        iconSmallRes = R.drawable.icon_snowing_heavy_fill1_wght400,
        iconLargeRes = R.drawable.icon_snowing_heavy_fill0_wght200,
        gradientPrimary = snowShowerPrimary,
        gradientSecondary = snowShowerSecondary
    )
    data object ModerateThunderstorm : WeatherType(
        weatherDescRes = R.string.moderate_thunderstorm,
        iconSmallRes = R.drawable.icon_thunderstorm_fill1_wght400,
        iconLargeRes = R.drawable.icon_thunderstorm_fill0_wght200,
        gradientPrimary = thunderstormPrimary,
        gradientSecondary = thunderstormSecondary
    )
    data object SlightHailThunderstorm : WeatherType(
        weatherDescRes = R.string.slight_hail_thunderstorm,
        iconSmallRes = R.drawable.icon_thunderstorm_fill1_wght400,
        iconLargeRes = R.drawable.icon_thunderstorm_fill0_wght200,
        gradientPrimary = thunderstormWithHailPrimary,
        gradientSecondary = thunderstormWithHailSecondary
    )
    data object HeavyHailThunderstorm : WeatherType(
        weatherDescRes = R.string.heavy_hail_thunderstorm,
        iconSmallRes = R.drawable.icon_thunderstorm_fill1_wght400,
        iconLargeRes = R.drawable.icon_thunderstorm_fill0_wght200,
        gradientPrimary = thunderstormWithHailPrimary,
        gradientSecondary = thunderstormWithHailSecondary
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