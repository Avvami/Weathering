package com.personal.weathering.weather.presenation.weather_details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.personal.weathering.R
import com.personal.weathering.core.domain.models.TabItem
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.ApplySystemBarsTheme
import com.personal.weathering.core.util.WindowInfo
import com.personal.weathering.ui.theme.nothingPrimary
import com.personal.weathering.ui.theme.nothingSecondary
import com.personal.weathering.ui.theme.onSurfaceLight
import com.personal.weathering.weather.presenation.WeatherState
import com.personal.weathering.weather.presenation.weather_details.components.CustomScrollableTabRow
import com.personal.weathering.weather.presenation.weather_details.components.CustomTabRow
import com.personal.weathering.weather.presenation.weather_details.components.HumidityDetails
import com.personal.weathering.weather.presenation.weather_details.components.PressureDetails
import com.personal.weathering.weather.presenation.weather_details.components.SunDetails
import com.personal.weathering.weather.presenation.weather_details.components.TemperatureDetails
import com.personal.weathering.weather.presenation.weather_details.components.WindDetails
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WeatherDetailsScreen(
    windowInfo: () -> WindowInfo,
    preferencesState: State<PreferencesState>,
    weatherState: () -> WeatherState,
    navigateBack: () -> Unit
) {
    val weatherDetailsViewModel: WeatherDetailsViewModel = viewModel()
    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    ApplySystemBarsTheme(applyLightStatusBars = false)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.weekly_forecast),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                    navigationIconContentColor = onSurfaceLight,
                    actionIconContentColor = onSurfaceLight,
                    titleContentColor = onSurfaceLight
                ),
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = topAppBarScrollBehavior
            )
        },
        contentColor = onSurfaceLight
    ) { innerPadding ->
        val radialGradient by remember(weatherDetailsViewModel.selectedDayOfWeek) {
            mutableStateOf(
                object : ShaderBrush() {
                    override fun createShader(size: Size): Shader {
                        val biggerDimension = maxOf(size.height, size.width)
                        return RadialGradientShader(
                            colors = listOf(
                                weatherState().weatherInfo?.dailyWeatherData?.get(weatherDetailsViewModel.selectedDayOfWeek)?.weatherType?.gradientSecondary ?: nothingSecondary,
                                weatherState().weatherInfo?.dailyWeatherData?.get(weatherDetailsViewModel.selectedDayOfWeek)?.weatherType?.gradientPrimary ?: nothingPrimary
                            ),
                            center = Offset(size.width, 0f),
                            radius = biggerDimension
                        )
                    }
                }
            )
        }
        Column(
            modifier = Modifier
                .background(radialGradient)
                .padding(innerPadding)
        ) {
            val tabItems by remember {
                derivedStateOf {
                    weatherState().weatherInfo?.dailyWeatherData?.map { dailyWeatherData ->
                        TabItem(
                            dayOfMonth = dailyWeatherData.time.dayOfMonth,
                            dayOfWeek = dailyWeatherData.time.format(DateTimeFormatter.ofPattern("EEE")).replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            }
                        )
                    } ?: listOf()
                }
            }
            val horizontalPagerState = rememberPagerState(
                initialPage = weatherDetailsViewModel.selectedDayOfWeek,
                pageCount = { tabItems.size }
            )
            LaunchedEffect(key1 = weatherDetailsViewModel.selectedDayOfWeek) {
                horizontalPagerState.animateScrollToPage(weatherDetailsViewModel.selectedDayOfWeek)
            }
            LaunchedEffect(key1 = horizontalPagerState.currentPage, key2 = horizontalPagerState.targetPage) {
                if (horizontalPagerState.currentPage == horizontalPagerState.targetPage)
                    weatherDetailsViewModel.weatherDetailsUiEvent(
                        WeatherDetailsUiEvent.SetSelectedDayOfWeek(horizontalPagerState.currentPage)
                    )
            }
            CompositionLocalProvider(
                LocalOverscrollConfiguration provides null
            ) {
                if (windowInfo().screenWidthInfo is WindowInfo.WindowType.Compact) {
                    CustomScrollableTabRow(
                        selectedDayOfWeek = weatherDetailsViewModel::selectedDayOfWeek,
                        tabItems = { tabItems },
                        weatherDetailsUiEvent = weatherDetailsViewModel::weatherDetailsUiEvent
                    )
                } else {
                    CustomTabRow(
                        selectedDayOfWeek = weatherDetailsViewModel::selectedDayOfWeek,
                        tabItems = { tabItems },
                        weatherDetailsUiEvent = weatherDetailsViewModel::weatherDetailsUiEvent
                    )
                }
            }
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = horizontalPagerState,
                verticalAlignment = Alignment.Top,
                key = { tabItems[it].dayOfMonth }
            ) { index ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(top = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    weatherState().weatherInfo?.let { weatherInfo ->
                        weatherInfo.dailyWeatherSummaryData[index]?.let { dailyWeatherSummaryData ->
                            item {
                                TemperatureDetails(
                                    preferencesState = preferencesState,
                                    summaryData = dailyWeatherSummaryData
                                )
                            }
                            item {
                                WindDetails(
                                    preferencesState = preferencesState,
                                    summaryData = dailyWeatherSummaryData
                                )
                            }
                            item {
                                PressureDetails(
                                    preferencesState = preferencesState,
                                    summaryData = dailyWeatherSummaryData
                                )
                            }
                            item {
                                HumidityDetails(
                                    summaryData = dailyWeatherSummaryData
                                )
                            }
                            item {
                                SunDetails(
                                    preferencesState = preferencesState,
                                    dailyWeatherData = weatherInfo.dailyWeatherData[index]
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}