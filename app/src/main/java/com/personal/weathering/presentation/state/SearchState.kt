package com.personal.weathering.presentation.state

import com.personal.weathering.domain.models.search.SearchInfo

data class SearchState(
    val searchInfo: SearchInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
