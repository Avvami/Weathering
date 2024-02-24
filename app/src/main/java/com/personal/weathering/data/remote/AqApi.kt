package com.personal.weathering.data.remote

import com.personal.weathering.data.models.AqDto
import retrofit2.http.GET
import retrofit2.http.Query

interface AqApi {

    @GET("v1/air-quality?current=european_aqi,us_aqi,pm10,pm2_5,carbon_monoxide,nitrogen_dioxide,sulphur_dioxide,ozone" +
            "&hourly=pm10,pm2_5,carbon_monoxide,nitrogen_dioxide,sulphur_dioxide,ozone,european_aqi,us_aqi&timezone=auto&forecast_days=3")
    suspend fun getAQData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): AqDto
}