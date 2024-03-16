package com.personal.weathering.data.remote

import com.personal.weathering.data.models.GeocodingDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApi {

    @GET("reverse?format=jsonv2&accept-language=en")
    suspend fun getCurrentLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): GeocodingDto
}