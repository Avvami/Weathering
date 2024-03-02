package com.personal.weathering.presentation.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.weathering.domain.models.search.SearchInfo
import com.personal.weathering.domain.repository.SearchRepository
import com.personal.weathering.domain.util.Resource
import com.personal.weathering.presentation.state.SearchState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository
): ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    var searchFieldActive by mutableStateOf(true)
        private set

    var searchState by mutableStateOf(SearchState())
        private set

    private var searchJob: Job? = null

    var isLanguageDropdownExpanded by mutableStateOf(false)
        private set

    private fun searchLocation(query: String, language: String) {
        if (query.isBlank()) {
            searchState = SearchState()
        } else {
            viewModelScope.launch {
                searchState = searchState.copy(
                    isLoading = true,
                    error = null
                )

                var searchInfo: SearchInfo? = null
                var error: String? = null

                searchRepository.getSearchData(query = query.trim(), language = language).let { result ->
                    when (result) {
                        is Resource.Error -> {
                            error = result.message
                        }
                        is Resource.Success -> {
                            searchInfo = result.data
                        }
                    }
                }

                searchState = searchState.copy(
                    searchInfo = searchInfo,
                    isLoading = false,
                    error = error
                )
            }
        }
    }

    fun searchUiEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.OnSearchQueryChange -> {
                searchQuery = event.query
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(1000L)
                    searchLocation(searchQuery, event.languageCode)
                }
            }
            is SearchUiEvent.SetSearchFieldActive -> { searchFieldActive = event.active }
            is SearchUiEvent.SetLanguageDropdownExpanded -> { isLanguageDropdownExpanded = event.expanded }
            is SearchUiEvent.SetSearchLanguage -> {
                isLanguageDropdownExpanded = false
                searchLocation(searchQuery, event.languageCode)
            }
        }
    }
}