package com.personal.weathering.aq.data.repository

import android.content.Context
import com.personal.weathering.R
import com.personal.weathering.aq.data.mappers.toAqInfo
import com.personal.weathering.aq.data.remote.AqApi
import com.personal.weathering.aq.domain.models.AqInfo
import com.personal.weathering.aq.domain.repository.AqRepository
import com.personal.weathering.core.util.Resource
import com.personal.weathering.core.util.UiText
import retrofit2.HttpException
import java.io.IOException

class AqRepositoryImpl(
    private val aqApi: AqApi,
    private val context: Context
): AqRepository {
    override suspend fun getAqData(lat: Double, lon: Double): Resource<AqInfo> {
        return try {
            Resource.Success(
                data = aqApi.getAQData(
                    lat = lat,
                    lon = lon
                ).toAqInfo()
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