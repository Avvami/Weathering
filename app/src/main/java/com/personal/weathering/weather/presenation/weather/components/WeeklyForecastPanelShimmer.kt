package com.personal.weathering.weather.presenation.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.core.util.shimmerEffect

@Composable
fun WeeklyForecastPanelShimmer() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge)
            .shimmerEffect(
                primaryColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                secondaryColor = MaterialTheme.colorScheme.surfaceContainer
            )
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.weekly_forecast),
                style = MaterialTheme.typography.titleLarge,
                color = Color.Transparent
            )
            Box(
                modifier = Modifier.size(48.dp)
            )
        }
        repeat(7) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f, false)
                ) {
                    Text(
                        text = "Date",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Transparent
                    )
                    Text(
                        text = "Day of week",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Transparent
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
                ) {
                    Box(
                        modifier = Modifier
                            .widthIn(30.dp)
                            .size(22.dp)
                    )
                    Text(
                        modifier = Modifier.widthIn(min = 30.dp),
                        text = stringResource(id = R.string.temperature, 10),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = Color.Transparent
                    )
                    Text(
                        modifier = Modifier.widthIn(min = 30.dp),
                        text = stringResource(id = R.string.temperature, 10),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = Color.Transparent
                    )
                }
            }
        }
    }
}