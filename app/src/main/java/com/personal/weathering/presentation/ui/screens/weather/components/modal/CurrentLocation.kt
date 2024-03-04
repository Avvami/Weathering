package com.personal.weathering.presentation.ui.screens.weather.components.modal

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.domain.util.UnitsConverter
import com.personal.weathering.presentation.state.CurrentCityState
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.state.WeatherState
import com.personal.weathering.presentation.ui.theme.weatheringBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue
import kotlin.math.roundToInt

@Composable
fun CurrentLocation(
    currentCityState: State<CurrentCityState>,
    preferencesState: State<PreferencesState>,
    weatherState: () -> WeatherState
) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.my_cities),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(weatheringDarkBlue)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(weight = .8f, fill = false)
            ) {
                Text(
                    text = stringResource(id = R.string.current),
                    style = MaterialTheme.typography.bodySmall,
                    color = weatheringBlue
                )
                Text(
                    text = currentCityState.value.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = weatheringBlue
                )
            }
            weatherState().weatherInfo?.let { weatherInfo ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = weatherInfo.currentWeatherData.weatherType.iconSmallRes),
                        contentDescription = stringResource(id = weatherInfo.currentWeatherData.weatherType.weatherDescRes),
                        tint = weatheringBlue,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = stringResource(
                            id = R.string.temperature,
                            if (preferencesState.value.useCelsius) weatherInfo.currentWeatherData.temperature.roundToInt() else
                                UnitsConverter.toFahrenheit(weatherInfo.currentWeatherData.temperature)
                                    .roundToInt()
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        color = weatheringBlue
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}