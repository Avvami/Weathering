package com.personal.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface AQApi {

    @GET("v1/air-quality?hourly=pm10,pm2_5,carbon_monoxide,nitrogen_dioxide,sulphur_dioxide,us_aqi")
    suspend fun getAQData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): AQDto
}