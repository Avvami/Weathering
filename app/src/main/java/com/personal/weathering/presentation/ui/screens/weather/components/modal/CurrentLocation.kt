package com.personal.weathering.presentation.ui.screens.weather.components.modal

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.domain.util.findActivity
import com.personal.weathering.domain.util.shimmerEffect
import com.personal.weathering.presentation.MainActivity
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.state.WeatherState

@Composable
fun CurrentLocation(
    modifier: Modifier = Modifier,
    preferencesState: State<PreferencesState>,
    weatherState: () -> WeatherState,
    setUseLocation: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.my_cities),
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = MaterialTheme.shapes.large
                )
                .clickable { setUseLocation() }
                .padding(start = 16.dp, top = 12.dp, end = 4.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(weight = 1f, fill = false)
            ) {
                Text(
                    text = stringResource(id = R.string.current_location),
                    style = MaterialTheme.typography.bodySmall
                )
                if (weatherState().retrievingLocation) {
                    Text(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .shimmerEffect(
                                primaryColor = MaterialTheme.colorScheme.surfaceVariant,
                                secondaryColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .3f)
                            ),
                        text = "Great London",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Transparent
                    )
                } else {
                    Text(
                        text = preferencesState.value.currentLocationCity,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            val activity = LocalContext.current.findActivity() as MainActivity
            AnimatedContent(
                targetState = activity.hasPermissions(),
                label = "Location granted",
                transitionSpec = { scaleIn() + fadeIn() togetherWith scaleOut() + fadeOut() }
            ) { targetState ->
                if (targetState) {
                    Box(
                        modifier = Modifier.size(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_my_location_fill1_wght400),
                            contentDescription = "Location granted"
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier.size(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_location_disabled_fill1_wght400),
                            contentDescription = "Location not granted"
                        )
                    }
                }
            }
        }
    }
}