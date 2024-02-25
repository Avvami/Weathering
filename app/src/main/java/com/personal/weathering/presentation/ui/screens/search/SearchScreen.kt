package com.personal.weathering.presentation.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.personal.weathering.R
import com.personal.weathering.presentation.ui.theme.weatheringBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue3p
import com.personal.weathering.presentation.ui.theme.weatheringDarkBlue70p

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    navigateBack: () -> Unit
) {
    val searchViewModel: SearchViewModel = viewModel()
    val customTextSelectionColors = TextSelectionColors(
        handleColor = weatheringDarkBlue,
        backgroundColor = weatheringDarkBlue.copy(alpha = .2f)
    )
    Scaffold(
        contentColor = weatheringDarkBlue,
        containerColor = weatheringBlue
    ) {
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            SearchBar(
                query = searchViewModel.searchQuery,
                onQueryChange = { searchViewModel.searchUiEvent(SearchUiEvent.OnSearchQueryChange(it)) },
                onSearch = {},
                active = searchViewModel.searchFieldActive,
                onActiveChange = { if (!it) navigateBack() },
                modifier = Modifier.fillMaxWidth(),
                colors = SearchBarDefaults.colors(
                    containerColor = weatheringDarkBlue3p,
                    dividerColor = weatheringDarkBlue,
                    inputFieldColors = SearchBarDefaults.inputFieldColors(
                        focusedTextColor = weatheringDarkBlue,
                        cursorColor = weatheringDarkBlue,
                        focusedLeadingIconColor = weatheringDarkBlue,
                        focusedPlaceholderColor = weatheringDarkBlue70p,
                        focusedTrailingIconColor = weatheringDarkBlue,
                        unfocusedLeadingIconColor = weatheringDarkBlue70p,
                        unfocusedPlaceholderColor = weatheringDarkBlue70p,
                        unfocusedTextColor = weatheringDarkBlue70p,
                        unfocusedTrailingIconColor = weatheringDarkBlue70p
                    )
                ),
                placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },
                leadingIcon = {
                    IconButton(onClick = navigateBack, modifier = Modifier.padding(horizontal = 8.dp)) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = searchViewModel.searchQuery.isNotEmpty(),
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        IconButton(onClick = { searchViewModel.searchUiEvent(SearchUiEvent.ClearSearchQuery) }, modifier = Modifier.padding(horizontal = 8.dp)) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                }
            ) {

            }
        }
    }
}