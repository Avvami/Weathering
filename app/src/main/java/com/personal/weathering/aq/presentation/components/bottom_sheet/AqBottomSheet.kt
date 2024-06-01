package com.personal.weathering.aq.presentation.components.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.personal.weathering.aq.presentation.AqUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AqBottomSheet(
    isBottomSheetShown: () -> Boolean,
    isPollutantsExpanded: () -> Boolean,
    isUsAqScaleExpanded: () -> Boolean,
    isEuAqScaleExpanded: () -> Boolean,
    aqUiEvent: (AqUiEvent) -> Unit
) {
    if (isBottomSheetShown()) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxHeight(),
            onDismissRequest = { aqUiEvent(AqUiEvent.SetBottomSheetShown) },
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            scrimColor = Color.Transparent,
            tonalElevation = 0.dp,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                AqPollutants(
                    isPollutantsExpanded = isPollutantsExpanded,
                    aqUiEvent = aqUiEvent
                )
                AqUsScale(
                    isUsAqScaleExpanded = isUsAqScaleExpanded,
                    aqUiEvent = aqUiEvent
                )
                AqEuScale(
                    isEuAqScaleExpanded = isEuAqScaleExpanded,
                    aqUiEvent = aqUiEvent
                )
            }
        }
    }
}