package com.personal.weathering.presentation.ui.screens.weather_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.personal.weathering.R
import com.personal.weathering.domain.models.weather.DailyWeatherSummaryData
import com.personal.weathering.domain.util.UnitsConverter
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.ui.theme.onSurfaceLight
import com.personal.weathering.presentation.ui.theme.onSurfaceLight70p
import com.personal.weathering.presentation.ui.theme.surfaceLight
import com.personal.weathering.presentation.ui.theme.surfaceLight30p

@Composable
fun PressureDetails(
    preferencesState: State<PreferencesState>,
    summaryData: List<DailyWeatherSummaryData>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(surfaceLight30p)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(onSurfaceLight)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_thermostat_fill1_wght400),
                    contentDescription = stringResource(id = R.string.pressure),
                    tint = surfaceLight
                )
            }
            Text(
                text = stringResource(id = R.string.pressure),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            summaryData.fastForEach { data ->
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = data.period,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (preferencesState.value.useHpa) "%.1f".format(data.weatherSummary.pressure) else
                            "%.1f".format(UnitsConverter.toMetersPerSecond(data.weatherSummary.pressure)),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(id = if (preferencesState.value.useHpa) R.string.hPa_unit else R.string.mmHg_unit),
                        style = MaterialTheme.typography.bodySmall,
                        color = onSurfaceLight70p
                    )
                }
            }
        }
    }
}