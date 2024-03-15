package com.personal.weathering.presentation.ui.screens.aq

import android.graphics.Shader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.domain.util.ApplySystemBarsTheme
import com.personal.weathering.presentation.UiEvent
import com.personal.weathering.presentation.state.AqState
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.ui.components.PullToRefresh
import com.personal.weathering.presentation.ui.screens.aq.components.AqShimmer
import com.personal.weathering.presentation.ui.screens.aq.components.AqThreeDayForecast
import com.personal.weathering.presentation.ui.screens.aq.components.CurrentAqInfo
import com.personal.weathering.presentation.ui.theme.drizzlePrimary
import com.personal.weathering.presentation.ui.theme.drizzleSecondary
import com.personal.weathering.presentation.ui.theme.onSurfaceLight
import com.personal.weathering.presentation.ui.theme.onSurfaceLight70p

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AqScreen(
    preferencesState: State<PreferencesState>,
    aqState: () -> AqState,
    pullToRefreshState: () -> PullToRefreshState,
    navigateBack: () -> Unit,
    uiEvent: (UiEvent) -> Unit
) {
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
                                    aqState().aqInfo!!.currentAqData.usAqiType.gradientPrimary,
                                    aqState().aqInfo!!.currentAqData.usAqiType.gradientSecondary
                                ) else
                                listOf(
                                    aqState().aqInfo!!.currentAqData.europeanAqiType.gradientPrimary,
                                    aqState().aqInfo!!.currentAqData.europeanAqiType.gradientSecondary
                                )
                        },
                        center = Offset(size.width, 0f),
                        radius = biggerDimension
                    )
                }
            }
        )
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
                title = { Text(text = stringResource(id = R.string.aqi), fontWeight = FontWeight.Medium) },
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
                if (aqState().aqInfo == null && aqState().error == null && aqState().isLoading) {
                    item {
                        AqShimmer(
                            radialGradient = radialGradient,
                            innerPadding = innerPadding
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
                                .padding(top = innerPadding.calculateTopPadding(), bottom = 16.dp)
                        ) {
                            aqState().error?.let { error ->
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
                            aqState().aqInfo?.let { aqInfo ->
                                CurrentAqInfo(
                                    preferencesState = preferencesState,
                                    aqInfo = { aqInfo }
                                )
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
            PullToRefresh(
                pullToRefreshState = pullToRefreshState,
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding())
                    .align(Alignment.TopCenter)
            )
        }
    }
}