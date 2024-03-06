package com.personal.weathering.presentation.ui.screens.weather

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weathering.BuildConfig
import com.personal.weathering.R
import com.personal.weathering.domain.util.ApplySystemBarsTheme
import com.personal.weathering.presentation.UiEvent
import com.personal.weathering.presentation.state.AqState
import com.personal.weathering.presentation.state.CurrentCityState
import com.personal.weathering.presentation.state.FavoritesState
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.state.WeatherState
import com.personal.weathering.presentation.ui.components.ThinLinearProgressIndicator
import com.personal.weathering.presentation.ui.screens.weather.components.WeatherDetails
import com.personal.weathering.presentation.ui.screens.weather.components.WeatherTemperatureInfo
import com.personal.weathering.presentation.ui.screens.weather.components.WeatherWeeklyForecast
import com.personal.weathering.presentation.ui.screens.weather.components.modal.ModalDrawer
import com.personal.weathering.presentation.ui.theme.ExtendedTheme
import com.personal.weathering.presentation.ui.theme.onSurfaceLight
import com.personal.weathering.presentation.ui.theme.onSurfaceLight70p
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    currentCityState: State<CurrentCityState>,
    preferencesState: State<PreferencesState>,
    favoritesState: State<List<FavoritesState>>,
    weatherState: () -> WeatherState,
    aqState: () -> AqState,
    pullToRefreshState: () -> PullToRefreshState,
    navigateToAqScreen: () -> Unit,
    navigateToSearchScreen: () -> Unit,
    uiEvent: (UiEvent) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollState = rememberScrollState()
    if (drawerState.targetValue == DrawerValue.Closed && scrollState.value == 0 && preferencesState.value.isDark)
        ApplySystemBarsTheme(darkTheme = false)
    else
        ApplySystemBarsTheme(darkTheme = preferencesState.value.isDark)

    if (pullToRefreshState().isRefreshing) {
        LaunchedEffect(true) {
            uiEvent(UiEvent.LoadWeatherInfo(currentCityState.value.lat, currentCityState.value.lon))
        }
    }
    val scaleFraction = if (pullToRefreshState().isRefreshing) 1f else
        LinearOutSlowInEasing.transform(pullToRefreshState().progress).coerceIn(0f, 1f)

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawer(
                drawerState = drawerState,
                preferencesState = preferencesState,
                favoritesState = favoritesState,
                currentCityState = currentCityState,
                weatherState = weatherState,
                uiEvent = uiEvent,
                closeDrawer = { scope.launch { drawerState.close() } }
            )
        },
        drawerState = drawerState,
        gesturesEnabled = true
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(pullToRefreshState().nestedScrollConnection)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = currentCityState.value.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    colors = if (scrollState.value != 0 && preferencesState.value.isDark) {
                        TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent,
                            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                            actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                            titleContentColor = MaterialTheme.colorScheme.onSurface
                        )
                    } else {
                        TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent,
                            navigationIconContentColor = onSurfaceLight,
                            actionIconContentColor = onSurfaceLight,
                            titleContentColor = onSurfaceLight
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Drawer")
                        }
                    },
                    actions = {
                        IconButton(onClick = navigateToSearchScreen) {
                            Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = innerPadding.calculateBottomPadding())
                    .verticalScroll(scrollState)
            ) {
                Column {
                    weatherState().error?.let { error ->
                        Text(
                            text = error,
                            style = MaterialTheme.typography.bodyLarge,
                            color = onSurfaceLight70p,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(innerPadding)
                                .padding(vertical = 16.dp, horizontal = 24.dp)
                        )
                    }
                    weatherState().weatherInfo?.let { weatherInfo ->
                        val radialGradient = object : ShaderBrush() {
                            override fun createShader(size: Size): Shader {
                                val biggerDimension = maxOf(size.height, size.width)
                                return RadialGradientShader(
                                    colors = listOf(
                                        weatherInfo.currentWeatherData.weatherType.gradientPrimary,
                                        weatherInfo.currentWeatherData.weatherType.gradientSecondary
                                    ),
                                    center = Offset(size.width, 0f),
                                    radius = biggerDimension
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .background(
                                    brush = radialGradient,
                                    shape = RoundedCornerShape(
                                        bottomStart = 28.dp,
                                        bottomEnd = 28.dp
                                    )
                                )
                                .padding(top = innerPadding.calculateTopPadding(), bottom = 16.dp)
                        ) {
                            WeatherTemperatureInfo(
                                preferencesState = preferencesState,
                                weatherInfo = { weatherInfo }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            WeatherDetails(
                                preferencesState = preferencesState,
                                weatherInfo = { weatherInfo },
                                aqState = aqState,
                                navigateToAqScreen = navigateToAqScreen
                            )
                        }
                        Column {
                            WeatherWeeklyForecast(
                                preferencesState = preferencesState,
                                weatherInfo = { weatherInfo }
                            )
                            Text(
                                text = stringResource(
                                    id = R.string.app_version,
                                    BuildConfig.VERSION_NAME,
                                    BuildConfig.VERSION_CODE
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.outline,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp, horizontal = 16.dp)
                            )
                        }
                    }
                }
                AnimatedVisibility(
                    modifier = Modifier
                        .padding(top = innerPadding.calculateTopPadding()),
                    visible = weatherState().isLoading
                ) {
                    ThinLinearProgressIndicator()
                }
                PullToRefreshContainer(
                    modifier = Modifier
                        .padding(top = innerPadding.calculateTopPadding())
                        .align(Alignment.TopCenter)
                        .graphicsLayer(scaleX = scaleFraction, scaleY = scaleFraction),
                    state = pullToRefreshState(),
                    containerColor = ExtendedTheme.colorScheme.surfaceContainerLow,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}