package com.personal.weathering.search.presentation

import com.personal.weathering.search.domain.models.SearchInfo

data class SearchState(
    val searchInfo: SearchInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
