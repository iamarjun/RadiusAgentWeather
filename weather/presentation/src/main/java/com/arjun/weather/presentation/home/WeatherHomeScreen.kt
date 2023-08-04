package com.arjun.weather.presentation.home

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.arjun.core_ui.LocalSpacing
import com.arjun.core_ui.theme.RadiusAgentPrimary
import com.arjun.weather.presentation.R
import com.arjun.weather.presentation.home.components.SearchTextField
import com.arjun.weather.presentation.home.components.SwipeBackground
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.flow.collectLatest

@Composable
@Destination
@RootNavGraph(start = true)
fun WeatherHomeScreen(
    modifier: Modifier,
    snackbarHostState: SnackbarHostState,
    navigator: WeatherHomeScreenNavigator,
    viewModel: WeatherHomeScreenViewModel = hiltViewModel()
) {
    Scaffold(modifier = modifier, snackbarHost = { SnackbarHost(snackbarHostState) }) {
        modifier.apply {
            padding(it)
        }
        HomeScreen(
            modifier = modifier,
            viewModel = viewModel,
            navigator = navigator,
            snackbarHostState = snackbarHostState
        )
    }
}

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalPermissionsApi::class,
    ExperimentalMaterial3Api::class,
)
@Composable
private fun HomeScreen(
    modifier: Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: WeatherHomeScreenViewModel,
    navigator: WeatherHomeScreenNavigator
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.setEvent(WeatherHomeScreenContract.Event.GetSavedLocations)
    }

    val spacing = LocalSpacing.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = keyboardController) {
        viewModel.effect.collectLatest {
            when (it) {
                is WeatherHomeScreenContract.Effect.NavigateToCurrentLocationWeatherDetailScreen -> navigator.navigateToWeatherDetailScreen(
                    it.slug
                )

                is WeatherHomeScreenContract.Effect.ShowToast -> {
                    snackbarHostState.showSnackbar(it.message)
                }
            }
        }
    }

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    LaunchedEffect(key1 = locationPermissions.allPermissionsGranted) {
        if (locationPermissions.allPermissionsGranted) {
            viewModel.getCurrentLocation()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {
            Spacer(modifier = modifier.height(100.dp))

            Text(
                modifier = modifier.padding(horizontal = spacing.spaceMedium),
                text = "Weather App",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = modifier.height(spacing.spaceMedium))

            SearchTextField(
                modifier = modifier.padding(horizontal = spacing.spaceSmall),
                text = state.query ?: "",
                onValueChange = {
                    viewModel.setEvent(WeatherHomeScreenContract.Event.OnQueryChange(it))
                },
                onSearch = {
                    keyboardController?.hide()
                    viewModel.setEvent(WeatherHomeScreenContract.Event.OnSearch)
                },
                onClear = {
                    keyboardController?.hide()
                    viewModel.setEvent(WeatherHomeScreenContract.Event.OnClearSearch)
                },
                onFocusChange = {
                    viewModel.setEvent(
                        WeatherHomeScreenContract.Event.OnSearchFocusChange(
                            it.isFocused
                        )
                    )
                },
                shouldShowHint = state.showHint,
            )

            Spacer(modifier = modifier.height(spacing.spaceSmall))

            Box {
                androidx.compose.animation.AnimatedVisibility(
                    visible = state.searchLocationResult.isEmpty() && state.savedLocations.isEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(spacing.spaceMedium),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            state.isSearching -> CircularProgressIndicator()
                            state.searchLocationResult.isEmpty() -> {
                                Column(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Search for a city or US/UK zip to check the weather",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Light
                                    )
                                    Spacer(modifier = modifier.height(spacing.spaceExtraLarge))
                                    Icon(
                                        modifier = modifier.size(88.dp),
                                        painter = painterResource(id = R.drawable.weather_mix),
                                        contentDescription = "Weather Placeholder"
                                    )
                                }
                            }
                        }
                    }
                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = state.searchLocationResult.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    LazyColumn(modifier = modifier.fillMaxWidth()) {
                        item {
                            ListItem(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (locationPermissions.allPermissionsGranted) viewModel.getCurrentLocation()
                                        else locationPermissions.launchMultiplePermissionRequest()
                                    }, headlineContent = {
                                    Text(
                                        text = "Current Location",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = RadiusAgentPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }, leadingContent = {
                                    Icon(
                                        imageVector = Icons.Default.NearMe,
                                        contentDescription = "Current Location",
                                        tint = RadiusAgentPrimary
                                    )
                                }
                            )
                        }

                        items(state.searchLocationResult) {
                            Column(modifier = modifier.fillMaxWidth()) {
                                ListItem(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.setEvent(
                                                WeatherHomeScreenContract.Event.OnSearchLocationResultClick(
                                                    slug = "${it.lat}, ${it.lon}"
                                                )
                                            )
                                        },
                                    headlineContent = {
                                        Text(
                                            text = "${it.name}, ${it.region},  ${it.country}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                    },
                                )
                            }
                            Divider(
                                modifier = modifier.padding(
                                    horizontal = spacing.spaceMedium, vertical = spacing.spaceSmall
                                )
                            )
                        }
                    }
                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = state.savedLocations.isNotEmpty() && state.searchLocationResult.isEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    LazyColumn(
                        modifier = modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(
                            items = state.savedLocations,
                            key = { it.latlon },
                            itemContent = {
                                val currentItem by rememberUpdatedState(newValue = it)
                                val dismissState = rememberDismissState(
                                    confirmValueChange = {
                                        viewModel.setEvent(
                                            WeatherHomeScreenContract.Event.OnItemDismissed(
                                                currentItem
                                            )
                                        )
                                        true
                                    }
                                )

                                Column(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.setEvent(
                                                WeatherHomeScreenContract.Event.OnSearchLocationResultClick(
                                                    currentItem.latlon
                                                )
                                            )
                                        },
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    SwipeToDismiss(
                                        modifier = modifier
                                            .fillMaxWidth(),
                                        state = dismissState,
                                        background = { SwipeBackground(dismissState = dismissState) },
                                        dismissContent = {
                                            Row(
                                                modifier = modifier
                                                    .fillMaxWidth()
                                                    .padding(spacing.spaceMedium),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column {
                                                    Text(
                                                        text = currentItem.location.name!!,
                                                        style = MaterialTheme.typography.headlineSmall
                                                    )
                                                    Spacer(modifier = modifier.height(spacing.spaceSmall))
                                                    Text(
                                                        text = "${currentItem.location.name!!}, ${currentItem.location.country!!}",
                                                        style = MaterialTheme.typography.bodySmall
                                                    )
                                                }
                                                Spacer(modifier = modifier.weight(1f))
                                                Column {
                                                    AsyncImage(
                                                        modifier = modifier.size(40.dp),
                                                        model = "https:${currentItem.current.condition?.icon}",
                                                        contentDescription = "Current Weather Condition"
                                                    )
                                                    Text(
                                                        text = "feels like ${currentItem.current.feelslikeC} °C",
                                                        style = MaterialTheme.typography.bodySmall
                                                    )
                                                }
                                            }
                                        }
                                    )
                                    Divider(modifier = modifier.fillMaxWidth())
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

