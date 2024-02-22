package com.personal.weatherapp.domain.repository

import com.personal.weatherapp.domain.airquality.AQInfo
import com.personal.weatherapp.domain.util.Resource

interface AqRepository {
    suspend fun getAQData(lat: Double, long: Double): Resource<AQInfo>
}