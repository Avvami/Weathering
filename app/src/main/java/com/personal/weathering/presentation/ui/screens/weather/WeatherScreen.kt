package com.personal.weathering.presentation.ui.screens.weather

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.personal.weathering.R
import com.personal.weathering.domain.util.timeFormat
import com.personal.weathering.presentation.state.AqState
import com.personal.weathering.presentation.state.WeatherState
import com.personal.weathering.presentation.ui.components.ThinLinearProgressIndicator
import com.personal.weathering.presentation.ui.theme.weatheringBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue3p
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue70p
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WeatherScreen(
    weatherState: () -> WeatherState,
    aqState: () -> AqState,
    navigateToAqScreen: () -> Unit
) {
    val weatherViewModel: WeatherViewModel = viewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = { /**/ },
        drawerState = drawerState,
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Krasnoyarsk", fontWeight = FontWeight.Medium) },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                        navigationIconContentColor = weatheringDarkBlue,
                        titleContentColor = weatheringDarkBlue
                    ),
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Drawer", tint = weatheringDarkBlue)
                        }
                    },
                    actions = {
                        IconButton(onClick = { /*Nothing*/ }) {
                            Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search", tint = weatheringDarkBlue)
                        }
                    },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            },
            containerColor = weatheringBlue,
            contentColor = weatheringDarkBlue
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                AnimatedVisibility(visible = weatherState().isLoading) {
                    ThinLinearProgressIndicator()
                }
                Box {
                    weatherState().error?.let { error ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = error,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .align(Alignment.TopCenter)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    weatherState().weatherInfo?.let { weatherInfo ->
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            item {
                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 24.dp)
                                    ) {
                                        Column {
                                            Text(
                                                text = stringResource(id = R.string.temperature, weatherInfo.currentWeatherData.temperature),
                                                fontSize = 82.sp
                                            )
                                            Text(
                                                text = stringResource(id = R.string.apparent_temperature, weatherInfo.currentWeatherData.apparentTemperature),
                                                style = MaterialTheme.typography.titleMedium,
                                                color = weatheringDarkBlue70p
                                            )
                                        }
                                        Column(
                                            horizontalAlignment = Alignment.End
                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.today_time, timeFormat(time = weatherInfo.currentWeatherData.time)),
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = weatherInfo.currentWeatherData.weatherType.weatherDesc,
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Icon(
                                        painter = painterResource(id = weatherInfo.currentWeatherData.weatherType.iconLargeRes),
                                        contentDescription = weatherInfo.currentWeatherData.weatherType.weatherDesc,
                                        modifier = Modifier
                                            .padding(horizontal = 24.dp)
                                            .size(200.dp)
                                            .align(Alignment.CenterHorizontally)
                                    )
                                    CompositionLocalProvider(
                                        LocalOverscrollConfiguration provides null
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .horizontalScroll(rememberScrollState())
                                                .height(intrinsicSize = IntrinsicSize.Max)
                                                .padding(horizontal = 24.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            weatherInfo.twentyFourHoursWeatherData.forEachIndexed { index, weatherData ->
                                                Column(
                                                    verticalArrangement = Arrangement.SpaceBetween,
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    modifier = Modifier.fillMaxHeight()
                                                ) {
                                                    Text(
                                                        text = timeFormat(time = weatherData.time),
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        color = weatheringDarkBlue70p
                                                    )
                                                    if (weatherData.time.hour == 0) {
                                                        Text(
                                                            text = weatherData.time.format(DateTimeFormatter.ofPattern("dd MMM")),
                                                            style = MaterialTheme.typography.bodyMedium,
                                                            color = weatheringDarkBlue70p
                                                        )
                                                    }
                                                    Icon(
                                                        painter = painterResource(id = weatherData.weatherType.iconSmallRes),
                                                        contentDescription = weatherData.weatherType.weatherDesc
                                                    )
                                                    Text(
                                                        text = stringResource(id = R.string.temperature, weatherData.temperature),
                                                        style = MaterialTheme.typography.titleMedium
                                                    )
                                                }
                                                if (index != weatherInfo.twentyFourHoursWeatherData.lastIndex) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.icon_fiber_manual_record_fill1_wght400),
                                                        contentDescription = "Divider",
                                                        modifier = Modifier
                                                            .padding(horizontal = 8.dp)
                                                            .size(8.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(intrinsicSize = IntrinsicSize.Min)
                                        .padding(horizontal = 24.dp)
                                        .clip(MaterialTheme.shapes.large)
                                        .background(weatheringDarkBlue)
                                        .padding(horizontal = 4.dp, vertical = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.icon_air_fill0_wght400),
                                            contentDescription = "Air",
                                            tint = weatheringBlue,
                                            modifier = Modifier.size(32.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = stringResource(id = R.string.km_per_hour, weatherInfo.currentWeatherData.windSpeed),
                                            style = MaterialTheme.typography.titleMedium,
                                            color = weatheringBlue
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.wind),
                                                style = MaterialTheme.typography.bodySmall,
                                                color = weatheringBlue
                                            )
                                            Icon(
                                                painter = painterResource(id = R.drawable.icon_navigation_fill1_wght400),
                                                contentDescription = "Direction",
                                                tint = weatheringBlue,
                                                modifier = Modifier
                                                    .size(12.dp)
                                                    .rotate(weatherInfo.currentWeatherData.windDirection)
                                            )
                                            Text(
                                                text = weatherInfo.currentWeatherData.windDirectionType.direction,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = weatheringBlue
                                            )
                                        }
                                    }
                                    VerticalDivider(color = weatheringBlue)
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.icon_thermostat_fill1_wght400),
                                            contentDescription = "Pressure",
                                            tint = weatheringBlue,
                                            modifier = Modifier.size(32.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = stringResource(id = R.string.hPa, weatherInfo.currentWeatherData.pressure),
                                            style = MaterialTheme.typography.titleMedium,
                                            color = weatheringBlue
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = stringResource(id = R.string.pressure),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = weatheringBlue
                                        )
                                    }
                                    VerticalDivider(color = weatheringBlue)
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = weatherInfo.currentWeatherData.humidityType.iconRes),
                                            contentDescription = weatherInfo.currentWeatherData.humidityType.iconDesc,
                                            tint = weatheringBlue,
                                            modifier = Modifier.size(32.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = stringResource(id = R.string.percent, weatherInfo.currentWeatherData.humidity),
                                            style = MaterialTheme.typography.titleMedium,
                                            color = weatheringBlue
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = stringResource(id = R.string.humidity),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = weatheringBlue
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp)
                                        .clip(MaterialTheme.shapes.large)
                                        .clickable { navigateToAqScreen() }
                                        .background(weatheringDarkBlue)
                                        .padding(
                                            start = 12.dp,
                                            top = 4.dp,
                                            end = 4.dp,
                                            bottom = 4.dp
                                        ),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_aq_fill0_wght400),
                                        contentDescription = "AQ",
                                        tint = weatheringBlue,
                                        modifier = Modifier.size(36.dp)
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        aqState().aqInfo?.let { aqInfo ->
                                            Icon(
                                                painter = painterResource(id = aqInfo.currentAqData.usAqiType.iconSmallRes),
                                                contentDescription = aqInfo.currentAqData.usAqiType.aqDesc,
                                                tint = weatheringBlue,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = aqInfo.currentAqData.usAqiType.aqDesc,
                                                style = MaterialTheme.typography.titleMedium,
                                                color = weatheringBlue
                                            )
                                        }
                                        Box(
                                            modifier = Modifier.size(48.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.icon_arrow_right_alt_fill0_wght400),
                                                contentDescription = "Arrow right",
                                                tint = weatheringBlue
                                            )
                                        }
                                    }
                                }
                            }
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp)
                                ) {
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
                                        weatherInfo.dailyWeatherData.forEachIndexed { index, weatherData ->
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(MaterialTheme.shapes.large)
                                                    .background(weatheringDarkBlue3p)
                                                    .padding(horizontal = 16.dp, vertical = 6.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column {
                                                    Text(
                                                        text = weatherData.time.format(DateTimeFormatter.ofPattern("d MMMM")),
                                                        style = MaterialTheme.typography.labelLarge,
                                                        color = weatheringDarkBlue70p
                                                    )
                                                    Text(
                                                        text = when (index) {
                                                            0 -> stringResource(id = R.string.today)
                                                            1 -> stringResource(id = R.string.tomorrow)
                                                            else -> weatherData.time.format(DateTimeFormatter.ofPattern("EEEE"))
                                                        },
                                                        style = MaterialTheme.typography.titleMedium,
                                                        color = weatheringDarkBlue
                                                    )
                                                }
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                                ) {
                                                    Icon(
                                                        painter = painterResource(id = weatherData.weatherType.iconSmallRes),
                                                        contentDescription = weatherData.weatherType.weatherDesc,
                                                        tint = weatheringDarkBlue,
                                                        modifier = Modifier.size(28.dp)
                                                    )
                                                    Column(
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        if (index == 0) {
                                                            Text(
                                                                text = stringResource(id = R.string.max),
                                                                style = MaterialTheme.typography.labelMedium,
                                                                color = weatheringDarkBlue70p
                                                            )
                                                        }
                                                        Text(
                                                            text = stringResource(id = R.string.temperature, weatherData.temperatureMax),
                                                            style = MaterialTheme.typography.titleLarge,
                                                            fontWeight = FontWeight.Medium,
                                                            color = weatheringDarkBlue
                                                        )
                                                    }
                                                    Column(
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        if (index == 0) {
                                                            Text(
                                                                text = stringResource(id = R.string.min),
                                                                style = MaterialTheme.typography.labelMedium,
                                                                color = weatheringDarkBlue70p
                                                            )
                                                        }
                                                        Text(
                                                            text = stringResource(id = R.string.temperature, weatherData.temperatureMin),
                                                            style = MaterialTheme.typography.titleLarge,
                                                            fontWeight = FontWeight.Medium,
                                                            color = weatheringDarkBlue70p
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}