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
import com.personal.weathering.domain.models.weather.DailyWeatherSummaryData
import com.personal.weathering.domain.util.UnitsConverter
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.ui.theme.onSurfaceLight
import com.personal.weathering.presentation.ui.theme.onSurfaceLight70p
import com.personal.weathering.presentation.ui.theme.surfaceLight
import com.personal.weathering.presentation.ui.theme.surfaceLight30p

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
                        text = stringResource(
                            id = if (preferencesState.value.useKmPerHour) R.string.km_per_hour else R.string.m_per_second,
                            if (preferencesState.value.useKmPerHour) data.weatherSummary.windSpeed else
                                UnitsConverter.toMetersPerSecond(data.weatherSummary.windSpeed)
                        ),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_navigation_fill1_wght400),
                            contentDescription = data.weatherSummary.windDirectionType.direction,
                            tint = onSurfaceLight70p,
                            modifier = Modifier
                                .size(12.dp)
                                .rotate(degrees = (data.weatherSummary.windDirection + 180) % 360)
                        )
                        Text(
                            text = data.weatherSummary.windDirectionType.direction,
                            style = MaterialTheme.typography.bodySmall,
                            color = onSurfaceLight70p
                        )
                    }
                }
            }
        }
    }
}
