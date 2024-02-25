package com.personal.weathering.presentation.ui.screens.search

sealed interface SearchUiEvent {
    data class SetSearchFieldActive(val active: Boolean): SearchUiEvent
    data class OnSearchQueryChange(val query: String): SearchUiEvent
    data object ClearSearchQuery: SearchUiEvent
}