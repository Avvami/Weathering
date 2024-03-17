package com.personal.weathering.presentation.ui.screens.aq

sealed interface AqUiEvent {
    data object SetAqDetailsExpanded: AqUiEvent
}