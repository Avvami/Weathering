package com.personal.weathering.presentation.ui.screens.weather

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weathering.BuildConfig
import com.personal.weathering.R
import com.personal.weathering.domain.util.ApplySystemBarsTheme
import com.personal.weathering.domain.util.C
import com.personal.weathering.domain.util.findActivity
import com.personal.weathering.presentation.MainActivity
import com.personal.weathering.presentation.UiEvent
import com.personal.weathering.presentation.state.AqState
import com.personal.weathering.presentation.state.FavoritesState
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.state.WeatherState
import com.personal.weathering.presentation.ui.components.PullToRefresh
import com.personal.weathering.presentation.ui.screens.weather.components.WeatherDetails
import com.personal.weathering.presentation.ui.screens.weather.components.WeatherShimmer
import com.personal.weathering.presentation.ui.screens.weather.components.WeatherTemperatureInfo
import com.personal.weathering.presentation.ui.screens.weather.components.WeatherWeeklyForecast
import com.personal.weathering.presentation.ui.screens.weather.components.modal.ModalDrawer
import com.personal.weathering.presentation.ui.theme.drizzlePrimary
import com.personal.weathering.presentation.ui.theme.drizzleSecondary
import com.personal.weathering.presentation.ui.theme.onSurfaceLight
import com.personal.weathering.presentation.ui.theme.onSurfaceLight70p
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    preferencesState: State<PreferencesState>,
    favoritesState: State<List<FavoritesState>>,
    weatherState: () -> WeatherState,
    aqState: () -> AqState,
    pullToRefreshState: () -> PullToRefreshState,
    navigateToWeatherDetailsScreen: (dayOfWeek: Int) -> Unit,
    navigateToAqScreen: () -> Unit,
    navigateToSearchScreen: () -> Unit,
    uiEvent: (UiEvent) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val lazyListState = rememberLazyListState()
    val isScrolledToTop by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset == 0
        }
    }
    if (pullToRefreshState().isRefreshing) {
        LaunchedEffect(true) {
            uiEvent(UiEvent.LoadWeatherInfo(preferencesState.value.useLocation, preferencesState.value.selectedCityLat, preferencesState.value.selectedCityLon))
        }
    }
    val radialGradient by remember(weatherState().weatherInfo) {
        mutableStateOf(
            object : ShaderBrush() {
                override fun createShader(size: Size): Shader {
                    val biggerDimension = maxOf(size.height, size.width)
                    return RadialGradientShader(
                        colors = if (weatherState().weatherInfo != null) listOf(
                            weatherState().weatherInfo!!.currentWeatherData.weatherType.gradientPrimary,
                            weatherState().weatherInfo!!.currentWeatherData.weatherType.gradientSecondary
                        ) else listOf(drizzlePrimary, drizzleSecondary),
                        center = Offset(size.width, 0f),
                        radius = biggerDimension
                    )
                }
            }
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawer(
                drawerState = drawerState,
                preferencesState = preferencesState,
                favoritesState = favoritesState,
                weatherState = weatherState,
                uiEvent = uiEvent,
                closeDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(pullToRefreshState().nestedScrollConnection)
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
            topBar = {
                if (drawerState.targetValue == DrawerValue.Closed && isScrolledToTop && preferencesState.value.isDark)
                    ApplySystemBarsTheme(applyLightStatusBars = false)
                else
                    ApplySystemBarsTheme(applyLightStatusBars = preferencesState.value.isDark)
                CenterAlignedTopAppBar(
                    title = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AnimatedVisibility(visible = preferencesState.value.useLocation) {
                                Text(
                                    text = stringResource(id = R.string.current_location),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Text(
                                text = if (preferencesState.value.useLocation) preferencesState.value.currentLocationCity else preferencesState.value.selectedCity,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    },
                    colors = if (!isScrolledToTop && preferencesState.value.isDark) {
                        TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = MaterialTheme.colorScheme.surface,
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
                    scrollBehavior = topAppBarScrollBehavior
                )
            },
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) { innerPadding ->
            Box {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = innerPadding.calculateBottomPadding()),
                    state = lazyListState
                ) {
                    if (weatherState().weatherInfo == null && weatherState().error == null && weatherState().isLoading) {
                        item {
                            WeatherShimmer(
                                radialGradient = radialGradient,
                                innerPadding = innerPadding,
                                navigateToAqScreen = navigateToAqScreen
                            )
                        }
                    } else {
                        item {
                            Column(
                                modifier = Modifier
                                    .background(
                                        brush = radialGradient,
                                        shape = RoundedCornerShape(
                                            bottomStart = 28.dp,
                                            bottomEnd = 28.dp
                                        )
                                    )
                                    .padding(
                                        top = innerPadding.calculateTopPadding(),
                                        bottom = 16.dp
                                    )
                            ) {
                                weatherState().error?.let { error ->
                                    Text(
                                        text = error,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = onSurfaceLight70p,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                                    )
                                }
                                weatherState().weatherInfo?.let { weatherInfo ->
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
                            }
                        }
                        item {
                            weatherState().weatherInfo?.let { weatherInfo ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    WeatherWeeklyForecast(
                                        preferencesState = preferencesState,
                                        weatherInfo = { weatherInfo },
                                        navigateToWeatherDetailsScreen = navigateToWeatherDetailsScreen
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
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
                                            .padding(vertical = 8.dp, horizontal = 16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                PullToRefresh(
                    pullToRefreshState = pullToRefreshState,
                    modifier = Modifier
                        .padding(top = innerPadding.calculateTopPadding())
                        .align(Alignment.TopCenter)
                )
            }
        }
    }
}