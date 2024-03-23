package com.personal.weathering.presentation.ui.screens.weather_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.personal.weathering.domain.models.TabItem
import com.personal.weathering.presentation.ui.components.TabRowDefaults
import com.personal.weathering.presentation.ui.screens.weather_details.WeatherDetailsUiEvent
import com.personal.weathering.presentation.ui.theme.onSurfaceLight
import com.personal.weathering.presentation.ui.theme.onSurfaceLight70p
import com.personal.weathering.presentation.ui.theme.surfaceLight
import com.personal.weathering.presentation.ui.theme.surfaceLight30p

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTabRow(
    selectedDayOfWeek: () -> Int,
    tabItems: () -> List<TabItem>,
    weatherDetailsUiEvent: (WeatherDetailsUiEvent) -> Unit
) {
    PrimaryTabRow(
        selectedTabIndex = selectedDayOfWeek(),
        containerColor = Color.Transparent,
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(selectedDayOfWeek()),
                color = onSurfaceLight,
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
            )
        },
        divider = {
            HorizontalDivider(color = surfaceLight30p)
        }
    ) {
        tabItems().fastForEachIndexed { index, item ->
            Tab(
                modifier = Modifier.clip(MaterialTheme.shapes.small),
                selected = index == selectedDayOfWeek(),
                onClick = {
                    weatherDetailsUiEvent(WeatherDetailsUiEvent.SetSelectedDayOfWeek(index))
                },
                selectedContentColor = onSurfaceLight,
                unselectedContentColor = onSurfaceLight70p
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .then(
                            if (index == selectedDayOfWeek())
                                Modifier.background(color = onSurfaceLight)
                            else Modifier
                        )
                        .align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.dayOfMonth.toString(),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (index == selectedDayOfWeek()) surfaceLight else onSurfaceLight70p
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