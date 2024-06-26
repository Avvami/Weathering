package com.personal.weathering.aq.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weathering.R
import com.personal.weathering.core.util.shimmerEffect
import com.personal.weathering.ui.theme.ExtendedTheme
import com.personal.weathering.ui.theme.onSurfaceLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AqShimmerCompact(
    radialGradient: ShaderBrush,
    innerPadding: PaddingValues
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
            .padding(top = innerPadding.calculateTopPadding())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "00",
                fontSize = 68.sp,
                color = Color.Transparent,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .shimmerEffect()
            )
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
                    text = stringResource(
                        id = R.string.aqi_rate,
                        stringResource(id = R.string.aqi),
                        stringResource(id = R.string.good)
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End,
                    color = Color.Transparent,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect()
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .size(160.dp)
                .align(Alignment.CenterHorizontally)
                .clip(MaterialTheme.shapes.large)
                .shimmerEffect()
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_format_quote_fill1_wght400),
                contentDescription = "Aq desc",
                tint = onSurfaceLight
            )
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
                text = stringResource(id = R.string.eu_good_aqi_general_description),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Transparent
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(2) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(MaterialTheme.shapes.large)
                        .shimmerEffect()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Box(modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(id = R.string.aq_units),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Transparent
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.aqi_rate),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Transparent
                    )
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth().size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.rotate(-90f),
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                contentDescription = "Expand more",
                tint = onSurfaceLight
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        BottomSheetDefaults.DragHandle(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(id = R.string.three_day_forecast),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(3) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.large)
                        .shimmerEffect(primaryColor = ExtendedTheme.colorScheme.surfaceContainerHighest, secondaryColor = ExtendedTheme.colorScheme.surfaceContainerLow)
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
                        Row(
                            modifier = Modifier.weight(weight = .5f, fill = false),
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(weight = .8f, fill = false),
                                text = stringResource(id = R.string.good),
                                style = MaterialTheme.typography.titleSmall,
                                textAlign = TextAlign.End,
                                color = Color.Transparent
                            )
                        }
                        Text(
                            text = "00",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium,
                            color = Color.Transparent
                        )
                        Text(
                            text = "00",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium,
                            color = Color.Transparent
                        )
                    }
                }
            }
        }
    }
}