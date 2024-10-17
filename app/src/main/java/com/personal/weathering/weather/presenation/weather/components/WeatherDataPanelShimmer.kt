package com.personal.weathering.weather.presenation.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.sp
import com.personal.weathering.R
import com.personal.weathering.core.util.shimmerEffect

@Composable
fun WeatherDataPanelShimmer() {
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.extraLarge)
            .shimmerEffect(
                primaryColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                secondaryColor = MaterialTheme.colorScheme.surfaceContainer
            )
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(26.dp)
                )
                Text(
                    text = "Great London",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Transparent,
                    maxLines = 1
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.temperature, 10),
                        fontSize = 68.sp,
                        color = Color.Transparent
                    )
                    Text(
                        text = stringResource(id = R.string.apparent_temperature, 10),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Transparent
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        text = "Clear sky",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.End,
                        color = Color.Transparent
                    )
                }
            }
            Box(modifier = Modifier.height(128.dp))
        }
    }

}