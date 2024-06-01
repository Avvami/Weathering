package com.personal.weathering.core.data.repository

import android.content.Context
import com.personal.weathering.R
import com.personal.weathering.core.data.mappers.toGeocodingInfo
import com.personal.weathering.core.data.remote.GeocodingApi
import com.personal.weathering.core.domain.models.GeocodingInfo
import com.personal.weathering.core.domain.repository.GeocodingRepository
import com.personal.weathering.core.util.Resource
import com.personal.weathering.core.util.UiText
import retrofit2.HttpException
import java.io.IOException

class GeocodingRepositoryImpl(
    private val geocodingApi: GeocodingApi,
    private val context: Context
): GeocodingRepository {
    override suspend fun getCurrentLocation(lat: Double, lon: Double): Resource<GeocodingInfo> {
        return try {
            Resource.Success(
                data = geocodingApi.getCurrentLocation(lat, lon).toGeocodingInfo()
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