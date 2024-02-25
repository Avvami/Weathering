package com.personal.weathering.presentation.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SearchViewModel: ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    var searchFieldActive by mutableStateOf(true)
        private set

    fun searchUiEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.OnSearchQueryChange -> { searchQuery = event.query }
            is SearchUiEvent.SetSearchFieldActive -> { searchFieldActive = event.active }
            SearchUiEvent.ClearSearchQuery -> {
                if (searchQuery.isNotEmpty()) {
                    searchQuery = ""
                } else {
                    searchFieldActive = false
                }
            }
        }
    }
}