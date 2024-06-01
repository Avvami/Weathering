package com.personal.weathering.search.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.personal.weathering.R
import com.personal.weathering.core.presentation.FavoritesState
import com.personal.weathering.search.presentation.SearchState

@Composable
fun SearchResults(
    favoritesState: State<List<FavoritesState>>,
    searchState: () -> SearchState,
    navigateBack: () -> Unit,
    setCurrentCityState: (cityId: Int, city: String, lat: Double, lon: Double) -> Unit,
    removeFavorite: (id: Int, cityId: Int, city: String, lat: Double, lon: Double) -> Unit,
    addFavorite: (cityId: Int, city: String, lat: Double, lon: Double) -> Unit,
    addToHistory: (cityId: Int, city: String, lat: Double, lon: Double) -> Unit
) {
    Box {
        searchState().error?.let { error ->
            Text(
                text = error,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }
        searchState().searchInfo?.let { searchInfo ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 12.dp, bottom = 16.dp)
            ) {
                if (searchInfo.searchResults == null) {
                    item {
                        Text(
                            text = stringResource(id = R.string.nothing_found),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        )
                    }
                } else {
                    items(searchInfo.searchResults) { searchResult ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    setCurrentCityState(searchResult.cityId, searchResult.name, searchResult.lat, searchResult.lon)
                                    addToHistory(searchResult.cityId, searchResult.name, searchResult.lat, searchResult.lon)
                                    navigateBack()
                                }
                                .padding(start = 16.dp, top = 2.dp, end = 4.dp, bottom = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val components = listOfNotNull(searchResult.name, searchResult.adminLevel, searchResult.country)
                            Text(
                                text = components.joinToString(separator = ", "),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.weight(fill = false, weight = .5f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                searchResult.countryCode?.let {
                                    Text(
                                        text = searchResult.countryCode,
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                AnimatedContent(
                                    targetState = favoritesState.value.any { it.cityId == searchResult.cityId },
                                    label = "Animate favorite",
                                    transitionSpec = { scaleIn() + fadeIn() togetherWith scaleOut() + fadeOut() }
                                ) {targetState ->
                                    if (targetState) {
                                        IconButton(
                                            onClick = {
                                                favoritesState.value.find { it.cityId == searchResult.cityId }?.let {
                                                    removeFavorite(it.id, searchResult.cityId, searchResult.name, searchResult.lat, searchResult.lon)
                                                }
                                            }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.icon_bookmark_remove_fill0_wght400),
                                                contentDescription = "Remove from favorite"
                                            )
                                        }
                                    } else {
                                        IconButton(
                                            onClick = {
                                                addFavorite(searchResult.cityId, searchResult.name, searchResult.lat, searchResult.lon)
                                            }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.icon_bookmark_add_fill0_wght400),
                                                contentDescription = "Add to favorite"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}