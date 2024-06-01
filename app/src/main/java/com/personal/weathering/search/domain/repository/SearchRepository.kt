package com.personal.weathering.search.domain.repository

import com.personal.weathering.search.domain.models.SearchInfo
import com.personal.weathering.core.util.Resource

interface SearchRepository {
    suspend fun getSearchData(query: String, language: String): Resource<SearchInfo>
}