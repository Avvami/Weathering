package com.personal.weathering.weather.presenation.weather_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.personal.weathering.R
import com.personal.weathering.weather.domain.models.DailyWeatherSummaryData
import com.personal.weathering.core.util.UnitsConverter
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.ui.theme.onSurfaceLight
import com.personal.weathering.ui.theme.onSurfaceLight70p
import com.personal.weathering.ui.theme.surfaceLight
import com.personal.weathering.ui.theme.surfaceLight30p

@Composable
fun WindDetails(
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
                    painter = painterResource(id = R.drawable.icon_air_fill0_wght400),
                    contentDescription = stringResource(id = R.string.wind),
                    tint = surfaceLight
                )
            }
            Text(
                text = stringResource(id = R.string.wind),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            summaryData.fastForEach { data ->
                data.weatherSummary.windSpeed?.let { windSpeed ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = data.periodRes),
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = stringResource(
                                id = if (preferencesState.value.useKmPerHour) R.string.km_per_hour else R.string.m_per_second,
                                if (preferencesState.value.useKmPerHour) windSpeed else
                                    UnitsConverter.toMetersPerSecond(windSpeed)
                            ),
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            data.weatherSummary.windDirection?.let { windDirection ->
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_navigation_fill1_wght400),
                                    contentDescription = stringResource(id = data.weatherSummary.windDirectionType.directionRes),
                                    tint = onSurfaceLight70p,
                                    modifier = Modifier
                                        .size(12.dp)
                                        .rotate(degrees = (windDirection + 180) % 360)
                                )
                            }
                            Text(
                                text = stringResource(id = data.weatherSummary.windDirectionType.directionRes),
                                style = MaterialTheme.typography.bodySmall,
                                color = onSurfaceLight70p
                            )
                        }
                    }
                }
            }
        }
    }
}
