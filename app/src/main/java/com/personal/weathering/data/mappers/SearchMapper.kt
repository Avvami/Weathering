package com.personal.weathering.data.mappers

import com.personal.weathering.data.models.SearchDto
import com.personal.weathering.data.models.SearchResultDto
import com.personal.weathering.domain.models.search.SearchInfo
import com.personal.weathering.domain.models.search.SearchResult

fun SearchDto.toSearchInfo(): SearchInfo {
    return SearchInfo(
        searchResults = searchResults?.map { it.toSearchResult() }
    )
}

fun SearchResultDto.toSearchResult(): SearchResult {
    return SearchResult(
        id, name, lat, lon, country, countryCode, adminLevel
    )
}