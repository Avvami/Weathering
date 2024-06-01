package com.personal.weathering.search.presentation

sealed interface SearchUiEvent {
    data class OnSearchQueryChange(val query: String, val languageCode: String): SearchUiEvent
    data class SetLanguageDropdownExpanded(val expanded: Boolean): SearchUiEvent
    data class SetSearchLanguage(val languageCode: String): SearchUiEvent
    data class RemoveFromHistory(val id: Int, val cityId: Int, val city: String, val lat: Double, val lon: Double):
        SearchUiEvent
    data class AddToHistory(val cityId: Int, val city: String, val lat: Double, val lon: Double):
        SearchUiEvent
}