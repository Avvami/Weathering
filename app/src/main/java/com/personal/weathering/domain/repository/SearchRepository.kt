package com.personal.weathering.domain.repository

import com.personal.weathering.domain.models.search.SearchInfo
import com.personal.weathering.domain.util.Resource

interface SearchRepository {
    suspend fun getSearchData(query: String): Resource<SearchInfo>
}