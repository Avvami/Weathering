package com.personal.weathering.weather.presenation.weather.components

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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.UnitsConverter
import com.personal.weathering.ui.theme.ExtendedTheme
import com.personal.weathering.weather.domain.models.WeatherInfo
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
fun LazyListScope.weatherWeeklyForecastCompat(
    preferencesState: State<PreferencesState>,
    weatherInfo: () -> WeatherInfo,
    navigateToWeatherDetailsScreen: (dayOfWeek: Int) -> Unit
) {
    item {
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
        }
    }
    itemsIndexed(
        items = weatherInfo().dailyWeatherData,
        key = { _, weatherData -> weatherData.uniqueKey }
    ) { index, weatherData ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 16.dp)
                .clip(MaterialTheme.shapes.large)
                .background(ExtendedTheme.colorScheme.surfaceContainerLow)
                .clickable { navigateToWeatherDetailsScreen(index) }
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            weatherData.time?.let { time ->
                Column(
                    modifier = Modifier.weight(.6f)
                ) {
                    Text(
                        text = time.format(DateTimeFormatter.ofPattern("d MMMM")),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = when (index) {
                            0 -> stringResource(id = R.string.today)
                            1 -> stringResource(id = R.string.tomorrow)
                            else -> time.format(DateTimeFormatter.ofPattern("EEEE")).replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            }
                        },
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Row(
                modifier = Modifier.weight(.4f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    modifier = Modifier.weight(1f).size(22.dp),
                    painter = painterResource(id = weatherData.weatherType.iconSmallRes),
                    contentDescription = stringResource(id = weatherData.weatherType.weatherDescRes)
                )
                weatherData.temperatureMax?.let { temperatureMax ->
                    Column(
                        modifier = Modifier.weight(1f),
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
                                if (preferencesState.value.useCelsius) temperatureMax.roundToInt() else
                                    UnitsConverter.toFahrenheit(temperatureMax).roundToInt()
                            ),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                weatherData.temperatureMin?.let { temperatureMin ->
                    Column(
                        modifier = Modifier.weight(1f),
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
                                if (preferencesState.value.useCelsius) temperatureMin.roundToInt() else
                                    UnitsConverter.toFahrenheit(temperatureMin).roundToInt()
                            ),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}