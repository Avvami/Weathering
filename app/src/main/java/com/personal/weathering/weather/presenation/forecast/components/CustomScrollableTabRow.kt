package com.personal.weathering.weather.presenation.forecast.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.personal.weathering.R
import com.personal.weathering.core.domain.models.TabItem
import com.personal.weathering.core.presentation.components.CustomPrimaryScrollableTabRow
import com.personal.weathering.weather.presenation.forecast.WeeklyForecastUiEvent

@Composable
fun CustomScrollableTabRow(
    selectedDayOfWeek: () -> Int,
    tabItems: () -> List<TabItem>,
    weatherDetailsUiEvent: (WeeklyForecastUiEvent) -> Unit
) {
    CustomPrimaryScrollableTabRow(
        modifier = Modifier.padding(bottom = 8.dp),
        selectedTabIndex = selectedDayOfWeek(),
        minItemWidth = 64.dp,
        edgePadding = 16.dp,
        containerColor = Color.Transparent,
        indicator = {},
        divider = {}
    ) {
        tabItems().fastForEachIndexed { index, item ->
            CustomTab(
                text = when (index) {
                    0 -> stringResource(id = R.string.today)
                    1 -> stringResource(id = R.string.tomorrow)
                    else -> item.dayOfWeek
                },
                selected = index == selectedDayOfWeek(),
                onClick = {
                    weatherDetailsUiEvent(WeeklyForecastUiEvent.SetSelectedDayOfWeek(index))
                }
            )
        }
    }
}

@Composable
fun CustomTab(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val animatedBackgroundColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainer,
        label = "Animated color"
    )
    val animatedTextColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        label = "Animated color"
    )
    Text(
        modifier = Modifier
            .clip(MaterialTheme.shapes.extraLarge)
            .background(animatedBackgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        text = text,
        color = animatedTextColor,
        fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
    )
}