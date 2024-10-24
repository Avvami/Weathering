package com.personal.weathering.weather.presenation.weather.components.drawer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
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
import com.personal.weathering.MainActivity
import com.personal.weathering.R
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.findActivity
import com.personal.weathering.core.util.shimmerEffect
import com.personal.weathering.weather.presenation.WeatherState

@Composable
fun CurrentLocation(
    modifier: Modifier = Modifier,
    preferencesState: State<PreferencesState>,
    weatherState: () -> WeatherState
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(weight = 1f, fill = false)
        ) {
            Text(
                text = stringResource(id = R.string.current_location),
                style = MaterialTheme.typography.bodyMedium
            )
            if (weatherState().retrievingLocation) {
                Text(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect(),
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
        Spacer(modifier = Modifier.width(4.dp))
        val activity = LocalContext.current.findActivity() as MainActivity
        Box(
            modifier = Modifier.minimumInteractiveComponentSize(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = activity.hasPermissions(),
                label = "Icon animation",
                transitionSpec = { scaleIn() + fadeIn() togetherWith scaleOut() + fadeOut() }
            ) { targetState ->
                if (targetState) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_my_location_fill1_wght400),
                        contentDescription = "Location granted"
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_location_disabled_fill1_wght400),
                        contentDescription = "Location not granted"
                    )
                }
            }
        }
    }
}