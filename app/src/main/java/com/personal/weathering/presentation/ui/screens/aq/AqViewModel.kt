package com.personal.weathering.presentation.ui.screens.aq

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AqViewModel: ViewModel() {

    var aqDetailsExpanded by mutableStateOf(false)
        private set

    fun aqUiEvent(event: AqUiEvent) {
        when (event) {
            AqUiEvent.SetAqDetailsExpanded -> { aqDetailsExpanded = !aqDetailsExpanded }
        }
    }
}