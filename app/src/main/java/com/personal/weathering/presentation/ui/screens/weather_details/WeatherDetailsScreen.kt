package com.personal.weathering.presentation.ui.screens.weather_details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
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
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.util.fastForEachIndexed
import androidx.lifecycle.viewmodel.compose.viewModel
import com.personal.weathering.R
import com.personal.weathering.domain.models.TabItem
import com.personal.weathering.domain.util.ApplySystemBarsTheme
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.state.WeatherState
import com.personal.weathering.presentation.ui.components.CustomPrimaryScrollableTabRow
import com.personal.weathering.presentation.ui.components.TabRowDefaults
import com.personal.weathering.presentation.ui.components.TabRowDefaults.tabIndicatorOffset
import com.personal.weathering.presentation.ui.screens.weather_details.components.HumidityDetails
import com.personal.weathering.presentation.ui.screens.weather_details.components.PressureDetails
import com.personal.weathering.presentation.ui.screens.weather_details.components.SunDetails
import com.personal.weathering.presentation.ui.screens.weather_details.components.TemperatureDetails
import com.personal.weathering.presentation.ui.screens.weather_details.components.WindDetails
import com.personal.weathering.presentation.ui.theme.drizzlePrimary
import com.personal.weathering.presentation.ui.theme.drizzleSecondary
import com.personal.weathering.presentation.ui.theme.onSurfaceLight
import com.personal.weathering.presentation.ui.theme.onSurfaceLight70p
import com.personal.weathering.presentation.ui.theme.surfaceLight
import com.personal.weathering.presentation.ui.theme.surfaceLight30p
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WeatherDetailsScreen(
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
                    Text(text = stringResource(id = R.string.weekly_forecast), fontWeight = FontWeight.Medium)
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
                                weatherState().weatherInfo?.dailyWeatherData?.get(weatherDetailsViewModel.selectedDayOfWeek)?.weatherType?.gradientPrimary ?: drizzlePrimary,
                                weatherState().weatherInfo?.dailyWeatherData?.get(weatherDetailsViewModel.selectedDayOfWeek)?.weatherType?.gradientSecondary ?: drizzleSecondary
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
                            dayOfWeek = dailyWeatherData.time.format(DateTimeFormatter.ofPattern("EEE"))
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
                CustomPrimaryScrollableTabRow(
                    selectedTabIndex = weatherDetailsViewModel.selectedDayOfWeek,
                    minItemWidth = 64.dp,
                    edgePadding = 0.dp,
                    containerColor = Color.Transparent,
                    indicator = { tabPositions ->
                        TabRowDefaults.PrimaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[weatherDetailsViewModel.selectedDayOfWeek]),
                            color = onSurfaceLight,
                            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
                        )
                    },
                    divider = {
                        HorizontalDivider(color = surfaceLight30p)
                    }
                ) {
                    tabItems.fastForEachIndexed { index, item ->
                        Tab(
                            modifier = Modifier.clip(MaterialTheme.shapes.small),
                            selected = index == weatherDetailsViewModel.selectedDayOfWeek,
                            onClick = {
                                weatherDetailsViewModel.weatherDetailsUiEvent(WeatherDetailsUiEvent.SetSelectedDayOfWeek(index))
                            },
                            selectedContentColor = onSurfaceLight,
                            unselectedContentColor = onSurfaceLight70p
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .then(
                                        if (index == weatherDetailsViewModel.selectedDayOfWeek)
                                            Modifier.background(color = onSurfaceLight)
                                        else Modifier
                                    )
                                    .align(Alignment.CenterHorizontally),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item.dayOfMonth.toString(),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = if (index == weatherDetailsViewModel.selectedDayOfWeek) surfaceLight else onSurfaceLight70p
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.dayOfWeek,
                                style = MaterialTheme.typography.labelLarge
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
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