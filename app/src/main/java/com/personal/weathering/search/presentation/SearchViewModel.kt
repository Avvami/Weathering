package com.personal.weathering.search.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.weathering.core.data.local.SearchResultEntity
import com.personal.weathering.search.domain.models.SearchInfo
import com.personal.weathering.core.domain.repository.LocalRepository
import com.personal.weathering.search.domain.repository.SearchRepository
import com.personal.weathering.core.util.Resource
import com.personal.weathering.core.presentation.SearchHistoryState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val localRepository: LocalRepository
): ViewModel() {

    private val _searchHistoryState = MutableStateFlow<List<SearchHistoryState>>(listOf())
    val searchHistoryState: StateFlow<List<SearchHistoryState>> = _searchHistoryState.asStateFlow()

    init {
        viewModelScope.launch {
            localRepository.getSearchHistory()
                .collect { searchResultsEntity ->
                    _searchHistoryState.value = searchResultsEntity.map {
                        SearchHistoryState(it.id, it.cityId, it.city, it.lat, it.lon)
                    }
                }
        }
    }

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
            is SearchUiEvent.SetLanguageDropdownExpanded -> { isLanguageDropdownExpanded = event.expanded }
            is SearchUiEvent.SetSearchLanguage -> {
                isLanguageDropdownExpanded = false
                searchLocation(searchQuery, event.languageCode)
            }
            is SearchUiEvent.AddToHistory -> {
                viewModelScope.launch {
                    localRepository.addToHistory(
                        SearchResultEntity(cityId = event.cityId, city = event.city, lat = event.lat, lon = event.lon)
                    )
                }
            }
            is SearchUiEvent.RemoveFromHistory -> {
                viewModelScope.launch {
                    localRepository.removeFromHistory(
                        SearchResultEntity(id = event.id, cityId = event.cityId, city = event.city, lat = event.lat, lon = event.lon)
                    )
                }
            }
        }
    }
}