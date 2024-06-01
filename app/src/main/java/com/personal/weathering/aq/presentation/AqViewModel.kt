package com.personal.weathering.aq.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AqViewModel: ViewModel() {

    var aqDetailsExpanded by mutableStateOf(false)
        private set

    var isBottomSheetShown by mutableStateOf(false)
        private set

    var isPollutantsExpanded by mutableStateOf(false)
        private set

    var isUsAqScaleExpanded by mutableStateOf(false)
        private set

    var isEuAqScaleExpanded by mutableStateOf(false)
        private set

    fun aqUiEvent(event: AqUiEvent) {
        when (event) {
            AqUiEvent.SetAqDetailsExpanded -> { aqDetailsExpanded = !aqDetailsExpanded }
            AqUiEvent.SetBottomSheetShown -> { isBottomSheetShown = !isBottomSheetShown }
            AqUiEvent.SetPollutantsExpanded -> { isPollutantsExpanded = !isPollutantsExpanded }
            AqUiEvent.SetEuAqScaleExpanded -> { isEuAqScaleExpanded = !isEuAqScaleExpanded }
            AqUiEvent.SetUsAqScaleExpanded -> { isUsAqScaleExpanded = !isUsAqScaleExpanded }
        }
    }
}