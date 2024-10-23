package com.personal.weathering.weather.presenation.weather.components.drawer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personal.weathering.MainActivity
import com.personal.weathering.R
import com.personal.weathering.UiEvent
import com.personal.weathering.core.presentation.FavoritesState
import com.personal.weathering.core.presentation.PreferencesState
import com.personal.weathering.core.util.LocationPermissionProvider
import com.personal.weathering.core.util.findActivity
import com.personal.weathering.openAppSettings
import com.personal.weathering.weather.presenation.WeatherState

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ModalDrawer(
    navigateToSettingsScreen: () -> Unit,
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
        drawerContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp)
        ) {
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
                            UiEvent.ShowDialog(
                                iconRes = LocationPermissionProvider().getIcon(),
                                messageRes = LocationPermissionProvider().getDescription(activity.shouldShowRationale()),
                                dismissTextRes = R.string.not_now,
                                onDismiss = { uiEvent(UiEvent.CloseDialog) },
                                confirmTextRes = if (activity.shouldShowRationale()) R.string.open_settings else R.string.grant,
                                onConfirm = {
                                    if (activity.shouldShowRationale()) {
                                        activity.openAppSettings()
                                        uiEvent(UiEvent.CloseDialog)
                                    } else {
                                        requestPermissions()
                                        uiEvent(UiEvent.CloseDialog)
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
        TopAppBar(
            modifier = Modifier.clickable { navigateToSettingsScreen() },
            title = {
                Text(
                    text = stringResource(id = R.string.settings),
                    fontWeight = FontWeight.Medium
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            actions = {
                Box(
                    modifier = Modifier.minimumInteractiveComponentSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        modifier = Modifier.rotate(90f),
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "More"
                    )
                }
            },
            windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
        )
    }
}