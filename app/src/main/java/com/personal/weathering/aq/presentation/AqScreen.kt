package com.personal.weathering.aq.presentation

import android.graphics.Shader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.personal.weathering.R
import com.personal.weathering.core.util.ApplySystemBarsTheme
import com.personal.weathering.core.util.UiText
import com.personal.weathering.core.util.WindowInfo
import com.personal.weathering.UiEvent
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.presentation.components.PullToRefresh
import com.personal.weathering.aq.presentation.components.bottom_sheet.AqBottomSheet
import com.personal.weathering.aq.presentation.components.AqShimmerCompact
import com.personal.weathering.aq.presentation.components.AqShimmerExpanded
import com.personal.weathering.aq.presentation.components.AqThreeDayForecast
import com.personal.weathering.aq.presentation.components.CurrentAqInfoCompact
import com.personal.weathering.aq.presentation.components.CurrentAqInfoExpanded
import com.personal.weathering.ui.theme.drizzlePrimary
import com.personal.weathering.ui.theme.drizzleSecondary
import com.personal.weathering.ui.theme.onSurfaceLight
import com.personal.weathering.ui.theme.onSurfaceLight70p

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AqScreen(
    windowInfo: () -> WindowInfo,
    preferencesState: State<PreferencesState>,
    aqState: () -> AqState,
    pullToRefreshState: () -> PullToRefreshState,
    navigateBack: () -> Unit,
    uiEvent: (UiEvent) -> Unit
) {
    val aqViewModel: AqViewModel = viewModel()
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val lazyListState = rememberLazyListState()
    val isScrolledToTop by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset == 0
        }
    }
    if (pullToRefreshState().isRefreshing) {
        LaunchedEffect(true) {
            if (preferencesState.value.useLocation)
                uiEvent(UiEvent.UpdateAqInfo(preferencesState.value.currentLocationLat, preferencesState.value.currentLocationLon))
            else
                uiEvent(UiEvent.UpdateAqInfo(preferencesState.value.selectedCityLat, preferencesState.value.selectedCityLon))
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState().nestedScrollConnection)
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            if (isScrolledToTop && preferencesState.value.isDark)
                ApplySystemBarsTheme(applyLightStatusBars = false)
            else
                ApplySystemBarsTheme(applyLightStatusBars = preferencesState.value.isDark)
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.aqi),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
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
                    IconButton(onClick = navigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { aqViewModel.aqUiEvent(AqUiEvent.SetBottomSheetShown) }) {
                        Icon(painter = painterResource(id = R.drawable.icon_info_fill0_wght400), contentDescription = "Info")
                    }
                },
                scrollBehavior = topAppBarScrollBehavior
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { innerPadding ->
        val radialGradient by remember(aqState().aqInfo) {
            mutableStateOf(
                object : ShaderBrush() {
                    override fun createShader(size: Size): Shader {
                        val biggerDimension = maxOf(size.height, size.width)
                        return RadialGradientShader(
                            colors = if (aqState().aqInfo == null)
                                listOf(drizzlePrimary, drizzleSecondary)
                            else {
                                if (preferencesState.value.useUSaq)
                                    listOf(
                                        aqState().aqInfo!!.currentAqData.usAqiType.gradientSecondary,
                                        aqState().aqInfo!!.currentAqData.usAqiType.gradientPrimary
                                    ) else
                                    listOf(
                                        aqState().aqInfo!!.currentAqData.euAqiType.gradientSecondary,
                                        aqState().aqInfo!!.currentAqData.euAqiType.gradientPrimary
                                    )
                            },
                            center = Offset(size.width, 0f),
                            radius = biggerDimension
                        )
                    }
                }
            )
        }
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = innerPadding.calculateBottomPadding()),
                state = lazyListState
            ) {
                if (aqState().aqInfo == null && aqState().error == null && aqState().isLoading) {
                    item {
                        if (windowInfo().screenWidthInfo is WindowInfo.WindowType.Compact) {
                            AqShimmerCompact(
                                radialGradient = radialGradient,
                                innerPadding = innerPadding
                            )
                        } else {
                            AqShimmerExpanded(
                                preferencesState = preferencesState,
                                radialGradient = radialGradient,
                                innerPadding = innerPadding
                            )
                        }
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
                                .padding(top = innerPadding.calculateTopPadding())
                        ) {
                            aqState().error?.let { error ->
                                if (aqState().aqInfo == null && error.contains(UiText.StringResource(R.string.api_call_error).asString())) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp),
                                        verticalArrangement = Arrangement.spacedBy(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(150.dp),
                                            painter = painterResource(id = R.drawable.icon_dino_offline),
                                            contentDescription = stringResource(id = R.string.no_internet),
                                            tint = onSurfaceLight
                                        )
                                        Text(
                                            text = stringResource(id = R.string.no_internet).uppercase(),
                                            style = MaterialTheme.typography.titleMedium,
                                            fontStyle = FontStyle.Italic,
                                            color = onSurfaceLight
                                        )
                                    }
                                } else {
                                    Text(
                                        text = error,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = onSurfaceLight70p,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 16.dp,
                                                top = 16.dp,
                                                end = 16.dp,
                                                bottom = 16.dp
                                            )
                                    )
                                }
                            }
                            aqState().aqInfo?.let { aqInfo ->
                                if (windowInfo().screenWidthInfo is WindowInfo.WindowType.Compact) {
                                    CurrentAqInfoCompact(
                                        preferencesState = preferencesState,
                                        aqInfo = { aqInfo },
                                        aqDetailsExpanded = aqViewModel::aqDetailsExpanded,
                                        aqUiEvent = aqViewModel::aqUiEvent
                                    )
                                } else {
                                    CurrentAqInfoExpanded(
                                        preferencesState = preferencesState,
                                        aqInfo = { aqInfo }
                                    )
                                }
                            }
                        }
                    }
                    item {
                        aqState().aqInfo?.let { aqInfo ->
                            AqThreeDayForecast(
                                preferencesState = preferencesState,
                                aqInfo = { aqInfo }
                            )
                        }
                    }
                }
            }
            AqBottomSheet(
                isBottomSheetShown = aqViewModel::isBottomSheetShown,
                isPollutantsExpanded = aqViewModel::isPollutantsExpanded,
                isUsAqScaleExpanded = aqViewModel::isUsAqScaleExpanded,
                isEuAqScaleExpanded = aqViewModel::isEuAqScaleExpanded,
                aqUiEvent = aqViewModel::aqUiEvent
            )
            PullToRefresh(
                pullToRefreshState = pullToRefreshState,
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding())
                    .align(Alignment.TopCenter)
            )
        }
    }
}