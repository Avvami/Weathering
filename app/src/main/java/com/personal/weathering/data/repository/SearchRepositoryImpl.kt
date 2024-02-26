package com.personal.weathering.data.repository

import android.content.Context
import com.personal.weathering.R
import com.personal.weathering.data.mappers.toSearchInfo
import com.personal.weathering.data.remote.SearchApi
import com.personal.weathering.domain.models.search.SearchInfo
import com.personal.weathering.domain.repository.SearchRepository
import com.personal.weathering.domain.util.Resource
import com.personal.weathering.domain.util.UiText
import retrofit2.HttpException
import java.io.IOException

class SearchRepositoryImpl(
    private val searchApi: SearchApi,
    private val context: Context
): SearchRepository {
    override suspend fun getSearchData(query: String, language: String): Resource<SearchInfo> {
        return try {
            Resource.Success(
                data = searchApi.getSearchData(query, language).toSearchInfo()
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
