package com.personal.weathering.weather.presenation.weather.components.drawer

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.weathering.R
import com.personal.weathering.core.util.LocationPermissionProvider
import com.personal.weathering.core.util.findActivity
import com.personal.weathering.MainActivity
import com.personal.weathering.UiEvent
import com.personal.weathering.openAppSettings
import com.personal.weathering.core.presentation.FavoritesState
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.weather.presenation.WeatherState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModalDrawer(
    drawerState: DrawerState,
    preferencesState: State<PreferencesState>,
    favoritesState: State<List<FavoritesState>>,
    weatherState: () -> WeatherState,
    closeDrawer: () -> Unit,
    requestPermissions: () -> Unit,
    uiEvent: (UiEvent) -> Unit
) {
    if (drawerState.targetValue == DrawerValue.Open) {
        BackHandler {
            closeDrawer()
        }
    }
    ModalDrawerSheet(
        drawerContentColor = MaterialTheme.colorScheme.onSurface,
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        drawerShape = RectangleShape
    ) {
        LazyColumn(
            contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        IconButton(onClick = { closeDrawer() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Close drawer"
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.settings),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    AnimatedContent(
                        targetState = preferencesState.value.isDark,
                        label = "Change theme anim",
                        transitionSpec = {
                            slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start, initialOffset = { it }) + scaleIn() togetherWith
                                    slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start) + scaleOut()
                        }
                    ) { targetState ->
                        if (targetState) {
                            IconButton(onClick = { uiEvent(UiEvent.SetDarkMode(false)) }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_light_mode_fill1_wght400),
                                    contentDescription = "Turn on light mode"
                                )
                            }
                        } else {
                            IconButton(onClick = { uiEvent(UiEvent.SetDarkMode(true)) }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_dark_mode_fill1_wght400),
                                    contentDescription = "Turn on dark mode"
                                )
                            }
                        }
                    }
                }
            }
            item {
                Units(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), preferencesState = preferencesState, uiEvent = uiEvent)
            }
            item {
                AQI(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), preferencesState = preferencesState, uiEvent = uiEvent)
            }
            item {
                Language(modifier = Modifier.padding(horizontal = 16.dp))
            }
            item {
                val activity = LocalContext.current.findActivity() as MainActivity
                CurrentLocation(
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 4.dp),
                    preferencesState = preferencesState,
                    weatherState = weatherState,
                    setUseLocation = {
                        if (activity.hasPermissions()) {
                            uiEvent(UiEvent.SetUseLocation)
                            closeDrawer()
                            return@CurrentLocation
                        }
                        uiEvent(
                            UiEvent.ShowMessageDialog(
                            iconRes = LocationPermissionProvider().getIcon(),
                            messageRes = LocationPermissionProvider().getDescription(activity.shouldShowRationale()),
                            dismissTextRes = R.string.not_now,
                            onDismiss = { uiEvent(UiEvent.CloseMessageDialog) },
                            confirmTextRes = if (activity.shouldShowRationale()) R.string.open_settings else R.string.grant,
                            onConfirm = {
                                if (activity.shouldShowRationale()) {
                                    activity.openAppSettings()
                                    uiEvent(UiEvent.CloseMessageDialog)
                                } else {
                                    requestPermissions()
                                    uiEvent(UiEvent.CloseMessageDialog)
                                }
                            }
                        ))
                    }
                )
            }
            if (favoritesState.value.isNotEmpty()) {
                items(
                    count = favoritesState.value.size,
                    key = { favoritesState.value[it].cityId }
                ) { index ->
                    val favorite = favoritesState.value[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .clip(MaterialTheme.shapes.large)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onSurface,
                                shape = MaterialTheme.shapes.large
                            )
                            .clickable {
                                uiEvent(
                                    UiEvent.SetSelectedCity(
                                        cityId = favorite.cityId,
                                        city = favorite.city,
                                        lat = favorite.lat,
                                        lon = favorite.lon
                                    )
                                )
                                closeDrawer()
                            }
                            .padding(start = 16.dp, top = 4.dp, end = 4.dp, bottom = 4.dp)
                            .animateItemPlacement(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.weight(weight = 1f, fill = false),
                            text = favorite.city,
                            style = MaterialTheme.typography.titleMedium
                        )
                        IconButton(
                            onClick = {
                                uiEvent(
                                    UiEvent.RemoveFavorite(
                                    id = favorite.id,
                                    cityId = favorite.cityId,
                                    city = favorite.city,
                                    lat = favorite.lat,
                                    lon = favorite.lon
                                ))
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_bookmark_remove_fill0_wght400),
                                contentDescription = "Remove from favorite"
                            )
                        }
                    }
                }
            }
        }
    }
}