package com.arjun.weather.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arjun.core_ui.LocalSpacing
import com.arjun.weather.presentation.R
import com.arjun.weather.presentation.home.components.SearchTextField
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

@OptIn(ExperimentalComposeUiApi::class)
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

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item { Spacer(modifier = modifier.height(100.dp)) }
        item {
            Text(
                text = "Weather App",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        }
        item { Spacer(modifier = modifier.height(spacing.spaceLarge)) }
        item {
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
                onFocusChange = {
                    viewModel.setEvent(WeatherHomeScreenContract.Event.OnSearchFocusChange(it.isFocused))
                },
                shouldShowHint = state.showHint,
            )
        }
        item { Spacer(modifier = modifier.height(spacing.spaceLarge)) }
        item {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    state.isSearching -> CircularProgressIndicator()
                    state.savedLocations.isEmpty() -> Icon(
                        modifier = modifier.size(88.dp),
                        painter = painterResource(id = R.drawable.weather_mix),
                        contentDescription = "Weather Placeholder"
                    )
                }
            }
        }
        item { Spacer(modifier = modifier.height(spacing.spaceExtraLarge)) }
        item {
            Text(
                text = "Search for a city or US/UK zip to check the weather",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Light
            )
        }
    }

}

