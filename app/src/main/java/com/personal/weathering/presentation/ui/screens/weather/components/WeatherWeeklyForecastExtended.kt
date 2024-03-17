package com.personal.weathering.presentation.ui.screens.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.domain.models.weather.WeatherInfo
import com.personal.weathering.domain.util.UnitsConverter
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.ui.theme.ExtendedTheme
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherWeeklyForecastExtended(
    preferencesState: State<PreferencesState>,
    weatherInfo: () -> WeatherInfo,
    navigateToWeatherDetailsScreen: (dayOfWeek: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        BottomSheetDefaults.DragHandle(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(id = R.string.weekly_forecast),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            weatherInfo().dailyWeatherData.forEachIndexed { index, weatherData ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.large)
                        .background(ExtendedTheme.colorScheme.surfaceContainerLow)
                        .clickable { navigateToWeatherDetailsScreen(index) }
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(.4f)
                    ) {
                        Text(
                            text = weatherData.time.format(DateTimeFormatter.ofPattern("d MMMM")),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = when (index) {
                                0 -> stringResource(id = R.string.today)
                                1 -> stringResource(id = R.string.tomorrow)
                                else -> weatherData.time.format(DateTimeFormatter.ofPattern("EEEE"))
                            },
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Row(
                        modifier = Modifier.weight(.6f, false),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.weight(.2f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_navigation_fill1_wght400),
                                contentDescription = weatherData.dominantWindDirectionType.direction,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .size(18.dp)
                                    .rotate(degrees = (weatherData.dominantWindDirection + 180) % 360)
                            )
                            Text(
                                text = weatherData.dominantWindDirectionType.direction,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Column(
                            modifier = Modifier.weight(.4f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (index == 0) {
                                Text(
                                    text = stringResource(id = R.string.wind),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = stringResource(
                                    id = if (preferencesState.value.useKmPerHour) R.string.km_per_hour else R.string.m_per_second,
                                    if (preferencesState.value.useKmPerHour) weatherData.windSpeedMax else
                                        UnitsConverter.toMetersPerSecond(weatherData.windSpeedMax)
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(
                            modifier = Modifier
                                .weight(.2f)
                                .size(28.dp),
                            painter = painterResource(id = weatherData.weatherType.iconSmallRes),
                            contentDescription = stringResource(id = weatherData.weatherType.weatherDescRes)
                        )
                        Column(
                            modifier = Modifier.weight(.2f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (index == 0) {
                                Text(
                                    text = stringResource(id = R.string.max),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = stringResource(
                                    id = R.string.temperature,
                                    if (preferencesState.value.useCelsius) weatherData.temperatureMax.roundToInt() else
                                        UnitsConverter.toFahrenheit(weatherData.temperatureMax).roundToInt()
                                ),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Column(
                            modifier = Modifier.weight(.2f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (index == 0) {
                                Text(
                                    text = stringResource(id = R.string.min),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = stringResource(
                                    id = R.string.temperature,
                                    if (preferencesState.value.useCelsius) weatherData.temperatureMin.roundToInt() else
                                        UnitsConverter.toFahrenheit(weatherData.temperatureMin).roundToInt()
                                ),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}