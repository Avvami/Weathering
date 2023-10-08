package com.personal.weatherapp.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weatherapp.R
import com.personal.weatherapp.presentation.WeatherState
import com.personal.weatherapp.presentation.ui.theme.*
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun WeatherScreen(
    state: WeatherState
) {
    state.weatherInfo?.currentWeatherData?.let { data ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(colorSurfaceWeather)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Krasnoyarsk ${data.time.format(DateTimeFormatter.ofPattern("dd HH:mm"))}",
                    color = colorOnSurfaceWeather,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Box(modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.extraLarge)
                    .background(colorOnSurfaceWeather)
                    .padding(12.dp, 4.dp)
                    .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = data.time.format(DateTimeFormatter.ofPattern("EEEE dd, MM")),
                        color = colorSurfaceWeather,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.align(Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = data.weatherType.weatherDesc,
                        style = MaterialTheme.typography.titleMedium,
                        color = colorOnSurfaceWeather
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        painter = painterResource(id = data.weatherType.iconRes),
                        contentDescription = data.weatherType.weatherDesc,
                        tint = colorOnSurfaceWeather,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "${data.temperatureCelsius.roundToInt()}°",
                    fontSize = 160.sp,
                    color = colorOnSurfaceWeather,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Box(modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.medium)
                    .background(colorOnSurfaceWeather)
                    .padding(24.dp)
                    .align(Alignment.CenterHorizontally)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_air_fill1),
                                contentDescription = "Wind Speed",
                                tint = colorSurfaceWeather,
                                modifier = Modifier.size(54.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "${data.windSpeed.roundToInt()}km/h", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = colorSurfaceWeather)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Wind", fontSize = 12.sp, color = colorSurfaceWeather)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_thermostat_fill1),
                                contentDescription = "Atmospheric pressure",
                                tint = colorSurfaceWeather,
                                modifier = Modifier.size(54.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "${data.pressure.roundToInt()}hPa", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = colorSurfaceWeather)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Atmospheric pressure", fontSize = 12.sp, color = colorSurfaceWeather)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(id = when {
                                    data.humidity < 33 -> R.drawable.ic_humidity_low_fill1
                                    data.humidity > 33 && data.humidity < 66 -> R.drawable.ic_humidity_mid_fill1
                                    data.humidity > 66 -> R.drawable.ic_humidity_high_fill1
                                    else -> R.drawable.ic_humidity_mid_fill1
                                }),
                                contentDescription = "Humidity",
                                tint = colorSurfaceWeather,
                                modifier = Modifier.size(54.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "${data.humidity.roundToInt()}%", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = colorSurfaceWeather)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Humidity", fontSize = 12.sp, color = colorSurfaceWeather)
                        }
                    }
                }
            }
        }
    }
}