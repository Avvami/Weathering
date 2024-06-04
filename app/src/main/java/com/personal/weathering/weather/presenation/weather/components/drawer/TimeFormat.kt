package com.personal.weathering.weather.presenation.weather.components.drawer

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.UiEvent
import com.personal.weathering.core.domain.models.DropdownItem
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.presentation.components.CustomDropdownMenu

@Composable
fun TimeFormat(
    modifier: Modifier = Modifier,
    preferencesState: State<PreferencesState>,
    uiEvent: (UiEvent) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.time_format), style = MaterialTheme.typography.titleMedium)
        OutlinedButton(
            onClick = { expanded = !expanded },
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface)
        ) {
            val rotation by animateIntAsState(targetValue = if (expanded) 90 else -90, label = "Rotation animation")
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .rotate(rotation.toFloat()),
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = if (preferencesState.value.use12hour) stringResource(id = R.string.twelve_hour) else stringResource(id = R.string.twenty_four_hour))
            CustomDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = !expanded },
                dropDownItems = listOf(
                    DropdownItem(
                        iconRes = R.drawable.icon_done_all_fill0_wght400,
                        text = stringResource(id = R.string.twelve_hour),
                        selected = preferencesState.value.use12hour,
                        onItemClick = {
                            expanded = false
                            uiEvent(UiEvent.SetTimeFormat(true))
                        }
                    ),
                    DropdownItem(
                        iconRes = R.drawable.icon_done_all_fill0_wght400,
                        text = stringResource(id = R.string.twenty_four_hour),
                        selected = !preferencesState.value.use12hour,
                        onItemClick = {
                            expanded = false
                            uiEvent(UiEvent.SetTimeFormat(false))
                        }
                    )
                )
            )
        }
    }
}