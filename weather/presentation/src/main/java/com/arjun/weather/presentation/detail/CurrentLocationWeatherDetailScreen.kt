package com.arjun.weather.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.arjun.core_ui.LocalSpacing
import com.arjun.weather.presentation.detail.components.UnitText
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun CurrentLocationWeatherDetailScreen(
    modifier: Modifier,
    slug: String,
    snackbarHostState: SnackbarHostState,
    navigator: CurrentLocationWeatherDetailScreenNavigator,
    viewModel: CurrentLocationWeatherDetailScreenViewModel = hiltViewModel()
) {


    LaunchedEffect(key1 = Unit) {
        viewModel.getCurrentLocationWeather(slug)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest {
            when (it) {
                else -> {}
            }
        }
    }

    val spacing = LocalSpacing.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = modifier,
                title = { },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Button"
                    )
                },
                actions = {
                    Row(
                        modifier = modifier.align(Alignment.CenterVertically),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Back Button"
                        )
                        Text(text = "Add")
                    }
                }
            )
        }
    ) {

        if (state.currentLocationWeather != null) {
            LazyColumn(
                modifier = modifier
                    .padding(it)
                    .padding(spacing.spaceMedium)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceLarge)
            ) {
                item {
                    Column {
                        Text(
                            text = state.currentLocationWeather?.location?.name!!,
                            style = MaterialTheme.typography.displaySmall
                        )
                        Spacer(modifier = modifier.height(spacing.spaceSmall))
                        Text(
                            text = "${state.currentLocationWeather?.location?.name!!}, ${state.currentLocationWeather?.location?.country!!}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                item {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        UnitText(
                            modifier = modifier,
                            value = "${state.currentLocationWeather?.current?.feelslikeC}",
                            unit = "°C"
                        )
                        Column {
                            AsyncImage(
                                modifier = modifier.size(64.dp),
                                model = "https:${state.currentLocationWeather?.current?.condition?.icon}",
                                contentDescription = "Current Weather Condition"
                            )
                            Text(
                                text = "feels like ${state.currentLocationWeather?.current?.feelslikeC} °C",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                }

            }
        } else {
            CircularProgressIndicator()
        }
    }
}