package com.personal.weathering.presentation.ui.screens.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weathering.BuildConfig
import com.personal.weathering.R
import com.personal.weathering.domain.util.shimmerEffect
import com.personal.weathering.presentation.ui.theme.onSurfaceLight
import com.personal.weathering.presentation.ui.theme.surfaceLight30p

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherShimmerExpanded(
    radialGradient: ShaderBrush,
    innerPadding: PaddingValues,
    navigateToAqScreen: () -> Unit
) {
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.temperature),
                        fontSize = 82.sp,
                        color = Color.Transparent,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.large)
                            .shimmerEffect()
                    )
                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.large)
                            .size(64.dp)
                            .shimmerEffect()
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = R.string.apparent_temperature),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Transparent,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect()
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(id = R.string.today_time, "12:00 AM"),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End,
                    color = Color.Transparent,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = R.string.clear_day_sky),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End,
                    color = Color.Transparent,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect()
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(MaterialTheme.shapes.large)
                .shimmerEffect()
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = "12:00 AM",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Transparent
                )
                Text(
                    text = "Date",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Transparent
                )
                Icon(
                    painter = painterResource(id = R.drawable.icon_wb_sunny_fill1_wght400),
                    contentDescription = null,
                    tint = Color.Transparent
                )
                Text(
                    text = stringResource(id = R.string.sunrise),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Transparent
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(intrinsicSize = IntrinsicSize.Min)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(MaterialTheme.shapes.large)
                    .shimmerEffect()
                    .padding(horizontal = 4.dp, vertical = 16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_thermostat_fill1_wght400),
                        contentDescription = "Pressure",
                        tint = Color.Transparent,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.mmHg),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Transparent,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(id = R.string.pressure),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Transparent
                    )
                }
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(MaterialTheme.shapes.large)
                    .clickable { navigateToAqScreen() }
                    .background(surfaceLight30p)
                    .padding(
                        start = 12.dp,
                        top = 4.dp,
                        end = 4.dp,
                        bottom = 4.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_aq_fill0_wght400),
                    contentDescription = "AQ",
                    tint = onSurfaceLight,
                    modifier = Modifier.size(36.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .shimmerEffect()
                    ) {
                        Box(
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(id = R.string.good),
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.Transparent
                        )
                    }
                    Box(
                        modifier = Modifier.size(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_arrow_right_alt_fill0_wght400),
                            contentDescription = "Arrow right",
                            tint = onSurfaceLight
                        )
                    }
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        BottomSheetDefaults.DragHandle(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(id = R.string.weekly_forecast),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(7) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.large)
                        .shimmerEffect()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Date",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.Transparent
                        )
                        Text(
                            text = stringResource(id = R.string.today),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Transparent
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_wb_sunny_fill1_wght400),
                            contentDescription = null,
                            tint = Color.Transparent,
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.temperature),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium,
                            color = Color.Transparent
                        )
                        Text(
                            text = stringResource(id = R.string.temperature),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium,
                            color = Color.Transparent
                        )
                    }
                }
            }
        }
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
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}