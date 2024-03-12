package com.personal.weathering.data.repository

import android.content.Context
import com.personal.weathering.R
import com.personal.weathering.data.mappers.toWeatherInfo
import com.personal.weathering.data.remote.WeatherApi
import com.personal.weathering.domain.repository.WeatherRepository
import com.personal.weathering.domain.util.Resource
import com.personal.weathering.domain.models.weather.WeatherInfo
import com.personal.weathering.domain.util.UiText
import retrofit2.HttpException
import java.io.IOException

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val context: Context
): WeatherRepository {
    override suspend fun getWeatherData(lat: Double, lon: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = weatherApi.getWeatherData(
                    lat = lat,
                    lon = lon
                ).toWeatherInfo(context)
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(e.message ?: UiText.StringResource(R.string.unknown_error).asString(context))
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(UiText.StringResource(R.string.api_call_error).asString(context))
        }
    }
}