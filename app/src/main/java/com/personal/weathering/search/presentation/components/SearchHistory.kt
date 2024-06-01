package com.personal.weathering.search.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
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
import com.personal.weathering.core.presentation.SearchHistoryState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchHistory(
    favoritesState: State<List<FavoritesState>>,
    searchHistoryState: State<List<SearchHistoryState>>,
    navigateBack: () -> Unit,
    setCurrentCityState: (cityId: Int, city: String, lat: Double, lon: Double) -> Unit,
    removeFavorite: (id: Int, cityId: Int, city: String, lat: Double, lon: Double) -> Unit,
    addFavorite: (cityId: Int, city: String, lat: Double, lon: Double) -> Unit,
    removeFromHistory: (id: Int, cityId: Int, city: String, lat: Double, lon: Double) -> Unit,
    addToHistory: (cityId: Int, city: String, lat: Double, lon: Double) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 12.dp, bottom = 16.dp)
    ) {
        item {
            Text(
                text = stringResource(id = R.string.search_history),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
        if (searchHistoryState.value.isEmpty()) {
            item {
                Text(
                    text = stringResource(id = R.string.empty),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
        } else {
            items(
                count = searchHistoryState.value.size,
                key = { searchHistoryState.value.reversed()[it].cityId }
            ) { index ->
                val searchResult = searchHistoryState.value.reversed()[index]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            setCurrentCityState(searchResult.cityId, searchResult.city, searchResult.lat, searchResult.lon)
                            addToHistory(searchResult.cityId, searchResult.city, searchResult.lat, searchResult.lon)
                            navigateBack()
                        }
                        .padding(start = 16.dp, top = 2.dp, end = 4.dp, bottom = 2.dp)
                        .animateItemPlacement(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = searchResult.city,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(fill = false, weight = .5f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimatedContent(
                            targetState = favoritesState.value.any { it.cityId == searchResult.cityId },
                            label = "Animate favorite",
                            transitionSpec = { scaleIn() + fadeIn() togetherWith scaleOut() + fadeOut() }
                        ) {targetState ->
                            if (targetState) {
                                IconButton(
                                    onClick = {
                                        favoritesState.value.find { it.cityId == searchResult.cityId }?.let {
                                            removeFavorite(it.id, searchResult.cityId, searchResult.city, searchResult.lat, searchResult.lon)
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
                                        addFavorite(searchResult.cityId, searchResult.city, searchResult.lat, searchResult.lon)
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_bookmark_add_fill0_wght400),
                                        contentDescription = "Add to favorite"
                                    )
                                }
                            }
                        }
                        IconButton(
                            onClick = {
                                removeFromHistory(searchResult.id, searchResult.cityId, searchResult.city, searchResult.lat, searchResult.lon)
                            }
                        ) {
                            Icon(imageVector = Icons.Rounded.Clear, contentDescription = "Remove from history")
                        }
                    }
                }
            }
        }
    }
}