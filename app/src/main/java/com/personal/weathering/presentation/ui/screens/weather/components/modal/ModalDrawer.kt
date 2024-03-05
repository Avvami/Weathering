package com.personal.weathering.presentation.ui.screens.weather.components.modal

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.presentation.UiEvent
import com.personal.weathering.presentation.state.CurrentCityState
import com.personal.weathering.presentation.state.FavoritesState
import com.personal.weathering.presentation.state.PreferencesState
import com.personal.weathering.presentation.state.WeatherState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModalDrawer(
    preferencesState: State<PreferencesState>,
    favoritesState: State<List<FavoritesState>>,
    currentCityState: State<CurrentCityState>,
    weatherState: () -> WeatherState,
    uiEvent: (UiEvent) -> Unit,
    closeDrawer: () -> Unit
) {
    ModalDrawerSheet(
        drawerContentColor = MaterialTheme.colorScheme.onSurface,
        drawerContainerColor = MaterialTheme.colorScheme.surface
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Units(preferencesState = preferencesState, uiEvent = uiEvent)
            }
            item {
                AQI(preferencesState = preferencesState, uiEvent = uiEvent)
            }
            item {
                CurrentLocation(
                    currentCityState = currentCityState,
                    preferencesState = preferencesState,
                    weatherState = weatherState,
                    setUseLocation = { useLocation ->
                        uiEvent(UiEvent.SetUseLocation(useLocation))
                    }
                )
            }
            if (favoritesState.value.isNotEmpty()) {
                items(
                    count = favoritesState.value.size,
                    key = { favoritesState.value.reversed()[it].cityId }
                ) { index ->
                    val favorite = favoritesState.value.reversed()[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(MaterialTheme.shapes.large)
                            .border(width = 1.dp, color = MaterialTheme.colorScheme.onSurface, shape = MaterialTheme.shapes.large)
                            .clickable {
                                uiEvent(UiEvent.SetCurrentCityState(
                                    cityId = favorite.cityId,
                                    city = favorite.city,
                                    lat = favorite.lat,
                                    lon = favorite.lon
                                ))
                                closeDrawer()
                            }
                            .padding(start = 16.dp, top = 4.dp, end = 4.dp, bottom = 4.dp)
                            .animateItemPlacement(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = favorite.city,
                            style = MaterialTheme.typography.titleMedium
                        )
                        IconButton(
                            onClick = {
                                uiEvent(UiEvent.RemoveFavorite(
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