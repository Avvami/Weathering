package com.personal.weathering.aq.presentation

sealed interface AqUiEvent {
    data object SetAqDetailsExpanded: AqUiEvent
    data object SetBottomSheetShown: AqUiEvent
    data object SetPollutantsExpanded: AqUiEvent
    data object SetUsAqScaleExpanded: AqUiEvent
    data object SetEuAqScaleExpanded: AqUiEvent
}