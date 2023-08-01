package com.arjun.weather.presentation.home

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arjun.core_ui.LocalSpacing
import com.arjun.core_ui.theme.RadiusAgentPrimary
import com.arjun.weather.presentation.R
import com.arjun.weather.presentation.home.components.SearchTextField
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
    navigator: WeatherHomeScreenNavigator,
    viewModel: WeatherHomeScreenViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier,
    ) {
        modifier.apply {
            padding(it)
        }
        HomeScreen(modifier = modifier, viewModel = viewModel, navigator = navigator)
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalPermissionsApi::class)
@Composable
private fun HomeScreen(
    modifier: Modifier,
    viewModel: WeatherHomeScreenViewModel,
    navigator: WeatherHomeScreenNavigator
) {

    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = keyboardController) {
        viewModel.effect.collectLatest {
//            when (it) {
//                WeatherHomeScreenContract.Effect.NavigateUp -> navigator.navigateUp()
//                is WeatherHomeScreenContract.Effect.ShowToast -> {
//                    scaffoldState.snackbarHostState.showSnackbar(it.message)
//                }
//            }
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
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {
            Spacer(modifier = modifier.height(100.dp))

            Text(
                text = "Weather App",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = modifier.height(spacing.spaceLarge))

            SearchTextField(
                modifier = modifier,
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

            Spacer(modifier = modifier.height(spacing.spaceLarge))

            Box {
                androidx.compose.animation.AnimatedVisibility(
                    visible = state.searchLocationResult.isEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(
                        modifier = modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            state.isSearching -> CircularProgressIndicator()
                            state.searchLocationResult.isEmpty() -> Icon(
                                modifier = modifier.size(88.dp),
                                painter = painterResource(id = R.drawable.weather_mix),
                                contentDescription = "Weather Placeholder"
                            )
                        }

                    }
                    Spacer(modifier = modifier.height(spacing.spaceExtraLarge))

                    Text(
                        text = "Search for a city or US/UK zip to check the weather",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Light
                    )
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
                                        if (locationPermissions.allPermissionsGranted)
                                            viewModel.getCurrentLocation()
                                        else
                                            locationPermissions.launchMultiplePermissionRequest()
                                    },
                                headlineContent = {
                                    Text(
                                        text = "Current Location",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = RadiusAgentPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                leadingContent = {
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
                                    horizontal = spacing.spaceMedium,
                                    vertical = spacing.spaceSmall
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

