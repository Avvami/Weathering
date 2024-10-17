package com.personal.weathering.core.presentation.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefresh(
    pullToRefreshState: () -> PullToRefreshState,
    modifier: Modifier = Modifier
) {
    val scaleFraction = if (pullToRefreshState().isRefreshing) 1f else
        LinearOutSlowInEasing.transform(pullToRefreshState().progress).coerceIn(0f, 1f)
    PullToRefreshContainer(
        modifier = modifier
            .graphicsLayer(scaleX = scaleFraction, scaleY = scaleFraction),
        state = pullToRefreshState(),
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        contentColor = MaterialTheme.colorScheme.onSurface
    )
}