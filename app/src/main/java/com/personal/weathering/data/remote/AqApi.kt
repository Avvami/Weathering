package com.personal.weathering.data.remote

import com.personal.weathering.data.models.AqDto
import retrofit2.http.GET
import retrofit2.http.Query

interface AqApi {

    @GET("v1/air-quality?current=european_aqi,us_aqi,pm10,pm2_5,carbon_monoxide,nitrogen_dioxide,sulphur_dioxide,ozone" +
            "&hourly=pm10,pm2_5,carbon_monoxide,nitrogen_dioxide,sulphur_dioxide,ozone,european_aqi,us_aqi,european_aqi_pm2_5," +
            "european_aqi_pm10,european_aqi_nitrogen_dioxide,european_aqi_ozone,european_aqi_sulphur_dioxide,us_aqi_pm2_5,us_aqi_pm10," +
            "us_aqi_nitrogen_dioxide,us_aqi_carbon_monoxide,us_aqi_ozone,us_aqi_sulphur_dioxide&timezone=auto&forecast_days=3")
    suspend fun getAQData(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double
    ): AqDto
}