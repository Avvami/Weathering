package com.personal.weathering.presentation.ui.screens.weather

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.domain.util.timeFormat
import com.personal.weathering.domain.weather.WeatherData
import com.personal.weathering.presentation.CurrentDateBox
import com.personal.weathering.presentation.ErrorBox
import com.personal.weathering.presentation.UIEvent
import com.personal.weathering.presentation.WeatherState
import com.personal.weathering.presentation.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.LocalDateTime
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun CurrentWeatherDetailsScreen(
    navigator: DestinationsNavigator,
    state: WeatherState,
    uiEvent: (UIEvent) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorSurfaceWeather)
        .safeDrawingPadding()
    ) {
        state.weatherInfo?.let { weatherInfo ->
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                weatherInfo.currentWeatherData?.let { data ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(shape = MaterialTheme.shapes.extraLarge)
                                .background(colorOnSurfaceWeather)
                                .padding(2.dp)
                                .clickable { navigator.popBackStack() }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back_fill1),
                                contentDescription = "Back",
                                tint = colorSurfaceWeather
                            )
                        }
                        CurrentDateBox(
                            time = data.time,
                            modifier = Modifier,
                            surfaceColor = colorSurfaceWeather,
                            onSurfaceColor = colorOnSurfaceWeather
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                weatherInfo.sunriseSunset?.let { data ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        SunData(iconRes = R.drawable.ic_wb_sunny_fill1, sunState = "Sunrise", time = timeFormat(data.sunrise))
                        SunData(iconRes = R.drawable.ic_wb_twilight_fill1, sunState = "Sunset", time = timeFormat(data.sunset))
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                weatherInfo.weatherDataPerDay[0]?.let { weatherData ->
                    if (weatherData.size == 24) {
                        CompositionLocalProvider(
                            LocalOverscrollConfiguration provides null
                        ) {
                            LazyColumn(contentPadding = PaddingValues(bottom = 32.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                val startIndex = weatherData.indexOfFirst { it.time.hour == LocalDateTime.now().hour }
                                item {
                                    CurrentDayWeatherPerHour(
                                        weatherData = weatherData[startIndex],
                                        contentColor = colorSurfaceWeather,
                                        modifier = Modifier
                                            .clip(shape = MaterialTheme.shapes.medium)
                                            .background(colorOnSurfaceWeather)
                                    )
                                }
                                items(weatherData.subList(startIndex + 1, weatherData.size)) { weatherData ->
                                    CurrentDayWeatherPerHour(
                                        weatherData = weatherData,
                                        contentColor = colorOnSurfaceWeather,
                                        modifier = Modifier.border(width = 2.dp, shape = MaterialTheme.shapes.medium, color = colorOnSurfaceWeather)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        state.weatherError?.let { error ->
            ErrorBox(
                error = error,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(32.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .background(colorOnSurfaceWeather),
                surfaceColor = colorSurfaceWeather,
                plainTextColor = colorPlainTextWeather,
                uiEvent = uiEvent
            )
            Log.i("Error to API request", error)
        }
    }
}

@Composable
fun SunData(
    iconRes: Int,
    sunState: String,
    time: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = sunState,
            tint = colorOnSurfaceWeather,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = sunState,
            style = MaterialTheme.typography.bodyLarge,
            color = colorOnSurfaceWeather
        )
        Text(
            text = time,
            style = MaterialTheme.typography.headlineSmall,
            color = colorOnSurfaceWeather
        )
    }
}

@Composable
fun CurrentDayWeatherPerHour(
    weatherData: WeatherData,
    contentColor: Color,
    modifier: Modifier
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(24.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = timeFormat(time = weatherData.time), style = MaterialTheme.typography.titleLarge, color = contentColor)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = weatherData.weatherType.iconRes),
                        contentDescription = weatherData.weatherType.weatherDesc,
                        tint = contentColor,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "${weatherData.temperatureCelsius.roundToInt()}°", style = MaterialTheme.typography.headlineMedium, color = contentColor)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_air_fill1),
                        contentDescription = "Wind Speed",
                        tint = contentColor
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "${weatherData.windSpeed.roundToInt()}m/s", style = MaterialTheme.typography.titleMedium, color = contentColor)
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_near_me_fill1),
                            contentDescription = weatherData.windDirection.direction,
                            tint = contentColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = weatherData.windDirection.direction, style = MaterialTheme.typography.labelMedium, color = contentColor)
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_thermostat_fill1),
                        contentDescription = "Pressure",
                        tint = contentColor
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "${weatherData.pressure.roundToInt()}mmHg", style = MaterialTheme.typography.titleMedium, color = contentColor)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = weatherData.humidityType.iconRes),
                        contentDescription = weatherData.humidityType.humidityDesc,
                        tint = contentColor
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "${weatherData.humidity}%", style = MaterialTheme.typography.titleMedium, color = contentColor)
                }
            }
        }
    }
}