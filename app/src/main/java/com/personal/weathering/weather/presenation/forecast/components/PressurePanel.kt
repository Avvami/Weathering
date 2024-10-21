package com.personal.weathering.weather.presenation.forecast.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.personal.weathering.R
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.convertToMmHg
import com.personal.weathering.core.util.formatDoubleValue
import com.personal.weathering.weather.domain.models.DailyWeatherSummaryData

@Composable
fun PressurePanel(
    preferencesState: State<PreferencesState>,
    summaryData: List<DailyWeatherSummaryData>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val annotatedString = buildAnnotatedString {
                append(stringResource(id = R.string.pressure))
                if (preferencesState.value.useHpa)
                    append(", ${stringResource(id = R.string.hPa_unit)}")
                else
                    append(", ${stringResource(id = R.string.mmHg_unit)}")
            }
            Text(
                text = annotatedString,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            summaryData.fastForEach { data ->
                data.weatherSummary.pressure?.let { pressure ->
                    Text(
                        modifier = Modifier.weight(1f),
                        text = formatDoubleValue(
                            if (preferencesState.value.useHpa) pressure else convertToMmHg(pressure)
                        ),
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}