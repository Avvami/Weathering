package com.personal.weathering.presentation.ui.screens.search

sealed interface SearchUiEvent {
    data class SetSearchFieldActive(val active: Boolean): SearchUiEvent
    data class OnSearchQueryChange(val query: String): SearchUiEvent
    data class SetLanguageDropdownExpanded(val expanded: Boolean): SearchUiEvent
    data class SetSearchLanguage(val languageCode: String): SearchUiEvent
}