package com.personal.weathering.weather.presenation.weather

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.personal.weathering.MainActivity
import com.personal.weathering.R
import com.personal.weathering.UiEvent
import com.personal.weathering.weather.presenation.AqState
import com.personal.weathering.core.presentation.FavoritesState
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.presentation.components.PullToRefresh
import com.personal.weathering.core.util.C
import com.personal.weathering.core.util.WindowInfo
import com.personal.weathering.core.util.findActivity
import com.personal.weathering.weather.presenation.WeatherState
import com.personal.weathering.weather.presenation.weather.components.AqPanel
import com.personal.weathering.weather.presenation.weather.components.WeatherDataPanel
import com.personal.weathering.weather.presenation.weather.components.WeatherDataPanelShimmer
import com.personal.weathering.weather.presenation.weather.components.WeeklyForecastPanel
import com.personal.weathering.weather.presenation.weather.components.WeeklyForecastPanelShimmer
import com.personal.weathering.weather.presenation.weather.components.drawer.ModalDrawer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    windowInfo: () -> WindowInfo,
    preferencesState: State<PreferencesState>,
    favoritesState: State<List<FavoritesState>>,
    weatherState: () -> WeatherState,
    aqState: () -> AqState,
    pullToRefreshState: () -> PullToRefreshState,
    navigateToSettingsScreen: () -> Unit,
    navigateToForecastScreen: (dayOfWeek: Int) -> Unit,
    navigateToSearchScreen: () -> Unit,
    requestPermissions: () -> Unit,
    uiEvent: (UiEvent) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    if (pullToRefreshState().isRefreshing) {
        LaunchedEffect(true) {
            uiEvent(UiEvent.LoadWeatherInfo(preferencesState.value.useLocation, preferencesState.value.selectedCityLat, preferencesState.value.selectedCityLon))
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawer(
                navigateToSettingsScreen = {
                    scope.launch { drawerState.close() }.invokeOnCompletion {
                        navigateToSettingsScreen()
                    }
                },
                drawerState = drawerState,
                preferencesState = preferencesState,
                favoritesState = favoritesState,
                weatherState = weatherState,
                closeDrawer = { scope.launch { drawerState.close() } },
                requestPermissions = requestPermissions,
                uiEvent = uiEvent
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(pullToRefreshState().nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                        actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                Icon(painter = painterResource(id = R.drawable.icon_segment_fill0_wght400), contentDescription = "Drawer")
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = navigateToSearchScreen) {
                            Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
                        }
                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    state = lazyListState,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues( horizontal = 16.dp, vertical = 8.dp)
                ) {
                    with(weatherState()) {
                        if (weatherInfo == null && isLoading) {
                            item {
                                WeatherDataPanelShimmer()
                            }
                            item {
                                WeeklyForecastPanelShimmer()
                            }
                        } else {
                            weatherInfo?.let { weatherInfo ->
                                item {
                                    WeatherDataPanel(
                                        preferencesState = preferencesState,
                                        weatherInfo = weatherInfo,
                                        retrievingLocation = retrievingLocation
                                    )
                                }
                            }
                            item {
                                AnimatedVisibility(
                                    modifier = Modifier.fillMaxWidth(),
                                    visible = aqState().aqInfo != null
                                ) {
                                    aqState().aqInfo?.let { aqInfo ->
                                        AqPanel(
                                            preferencesState = preferencesState,
                                            aqInfo = aqInfo,
                                            isCollapsed = { aqState().isCollapsed },
                                            uiEvent = uiEvent
                                        )
                                    }
                                }
                            }
                            weatherInfo?.dailyWeatherData?.let { forecast ->
                                item {
                                    WeeklyForecastPanel(
                                        navigateToForecastScreen = navigateToForecastScreen,
                                        preferencesState = preferencesState,
                                        forecast = forecast
                                    )
                                }
                            }
                            item {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    val activity = LocalContext.current.findActivity() as MainActivity
                                    Column(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = null
                                            ) {
                                                activity.openCustomWebTab(C.OM_URL)
                                            },
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.icon_open_meteo),
                                            contentDescription = stringResource(id = R.string.open_meteo),
                                            modifier = Modifier.size(54.dp)
                                        )
                                        Text(
                                            text = stringResource(id = R.string.open_meteo),
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                PullToRefresh(
                    pullToRefreshState = pullToRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}