package com.personal.weathering.weather.presenation.weather_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weathering.R
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.timeFormat
import com.personal.weathering.ui.theme.onSurfaceLight70p
import com.personal.weathering.ui.theme.surfaceLight30p
import com.personal.weathering.weather.domain.models.DailyWeatherData
import java.util.Locale

@Composable
fun SunDetails(
    preferencesState: State<PreferencesState>,
    dailyWeatherData: DailyWeatherData
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(surfaceLight30p)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        dailyWeatherData.daylightDuration?.let { daylightDuration ->
            Row {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.daylight_duration),
                        style = MaterialTheme.typography.bodyLarge,
                        color = onSurfaceLight70p
                    )
                    Text(
                        text = stringResource(
                            id = R.string.hours_minutes,
                            daylightDuration.toHours(),
                            daylightDuration.toMinutesPart()
                        ),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            dailyWeatherData.sunrise?.let { sunrise ->
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.icon_wb_sunny_fill1_wght400),
                        contentDescription = stringResource(id = R.string.sunrise)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(id = R.string.sunrise).replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = onSurfaceLight70p
                    )
                    Text(
                        text = timeFormat(time = sunrise, use12hour = preferencesState.value.use12hour),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            dailyWeatherData.sunset?.let { sunset ->
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.icon_wb_twilight_fill1_wght400),
                        contentDescription = stringResource(id = R.string.sunset)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(id = R.string.sunset).replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = onSurfaceLight70p
                    )
                    Text(
                        text = timeFormat(time = sunset, use12hour = preferencesState.value.use12hour),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}