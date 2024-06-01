package com.personal.weathering.search.data.mappers

import com.personal.weathering.search.data.models.SearchDto
import com.personal.weathering.search.data.models.SearchResultDto
import com.personal.weathering.search.domain.models.SearchInfo
import com.personal.weathering.search.domain.models.SearchResult

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