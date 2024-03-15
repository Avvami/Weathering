package com.personal.weathering.presentation.ui.screens.aq.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.domain.models.airquality.AqInfo
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.ui.theme.ExtendedTheme
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AqThreeDayForecast(
    preferencesState: State<PreferencesState>,
    aqInfo: () -> AqInfo
) {
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
            aqInfo().hourlyAqData.values.forEachIndexed { index, hourlyAqData ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.large)
                        .background(ExtendedTheme.colorScheme.surfaceContainerLow)
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(.25f)
                    ) {
                        Text(
                            text = hourlyAqData[0].time.format(DateTimeFormatter.ofPattern("d MMMM")),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = when (index) {
                                0 -> stringResource(id = R.string.today)
                                1 -> stringResource(id = R.string.tomorrow)
                                else -> hourlyAqData[0].time.format(DateTimeFormatter.ofPattern("EEEE"))
                            },
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Row(
                        modifier = Modifier.weight(.75f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.weight(weight = .6f),
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(weight = .8f),
                                text = if (preferencesState.value.useUSaq) stringResource(id = hourlyAqData.maxBy { it.usAqi }.usAqiType.aqDescRes) else
                                    stringResource(id = hourlyAqData.maxBy { it.europeanAqi }.europeanAqiType.aqDescRes),
                                style = MaterialTheme.typography.titleSmall,
                                textAlign = TextAlign.End
                            )
                            Icon(
                                painter = if (preferencesState.value.useUSaq) painterResource(id = hourlyAqData.maxBy { it.usAqi }.usAqiType.iconSmallRes) else
                                    painterResource(id = hourlyAqData.maxBy { it.europeanAqi }.europeanAqiType.iconSmallRes),
                                contentDescription = if (preferencesState.value.useUSaq) stringResource(id = hourlyAqData.maxBy { it.usAqi }.usAqiType.aqDescRes) else
                                    stringResource(id = hourlyAqData.maxBy { it.europeanAqi }.europeanAqiType.aqDescRes),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Column(
                            modifier = Modifier.weight(weight = .15f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (index == 0) {
                                Text(
                                    text = stringResource(id = R.string.max),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = if (preferencesState.value.useUSaq) hourlyAqData.maxBy { it.usAqi }.usAqi.toString() else
                                    hourlyAqData.maxBy { it.europeanAqi }.europeanAqi.toString(),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Column(
                            modifier = Modifier.weight(weight = .15f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (index == 0) {
                                Text(
                                    text = stringResource(id = R.string.min),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = if (preferencesState.value.useUSaq) hourlyAqData.minBy { it.usAqi }.usAqi.toString() else
                                    hourlyAqData.minBy { it.europeanAqi }.europeanAqi.toString(),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}