package com.arjun.weather.presentation.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.BeachAccess
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.arjun.core_ui.Dimensions
import com.arjun.core_ui.LocalSpacing
import com.arjun.core_ui.theme.RadiusAgentAlabaster
import com.arjun.core_ui.theme.RadiusAgentGeneralAccent
import com.arjun.core_ui.theme.RadiusAgentRed
import com.arjun.core_ui.theme.RadiusAgentSupportingGray
import com.arjun.core_ui.theme.RadiusAgentSupportingGray3
import com.arjun.weather.domain.model.Alert
import com.arjun.weather.presentation.detail.components.UnitText
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter


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
                CurrentLocationWeatherDetailScreenContract.Effect.NavigateUp -> {
                    navigator.navigateUp()
                }

                is CurrentLocationWeatherDetailScreenContract.Effect.ShowToast -> {
                    snackbarHostState.showSnackbar(it.message)
                }
            }
        }
    }

    BackHandler {
        navigator.navigateUp()
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
                        modifier = modifier.clickable {
                            viewModel.setEvent(CurrentLocationWeatherDetailScreenContract.Event.OnBackPress)
                        }, imageVector = Icons.Default.ArrowBack, contentDescription = "Back Button"
                    )
                },
                actions = {
                    Row(
                        modifier = modifier
                            .align(Alignment.CenterVertically)
                            .clickable {
                                if (!state.isSaved) {
                                    viewModel.setEvent(
                                        CurrentLocationWeatherDetailScreenContract.Event.SaveToDb(
                                            viewModel.currentState.currentLocationWeather
                                        )
                                    )
                                } else {
                                    viewModel.setEvent(
                                        CurrentLocationWeatherDetailScreenContract.Event.DeleteFromDb(
                                            viewModel.currentState.currentLocationWeather?.latlon
                                        )
                                    )
                                }
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!state.isSaved) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Save"
                            )
                            Text(text = "Add")
                        } else {
                            Icon(
                                imageVector = Icons.Default.DeleteOutline,
                                contentDescription = "Delete",
                                tint = RadiusAgentRed
                            )
                            Text(text = "Remove", color = RadiusAgentRed)
                        }
                    }
                }
            )
        }
    ) {

        if (state.isLoading) {
            Box(
                modifier = modifier
                    .padding(it)
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (state.currentLocationWeather != null) {
            LazyColumn(
                modifier = modifier
                    .padding(it)
                    .padding(spacing.spaceMedium)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceLarge)
            ) {
                item {
                    CurrentLocation(state, modifier, spacing)
                }

                item {
                    WeatherPreview(modifier, state)
                }

                item {
                    HourlyForecast(modifier, spacing, state)
                }

                item {
                    WeeklyForecast(modifier, spacing, state)
                }

                item {
                    WeatherMiscInfo(modifier, spacing, state)
                }

                item {
                    Column(
                        modifier = modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
                        horizontalAlignment = Alignment.Start
                    ) {

                        AlertHeader(modifier)

                        state.currentLocationWeather?.alerts?.alert?.filterNotNull()
                            ?.forEach { alert -> Alert(modifier, spacing, alert) }
                    }
                }
            }
        }
    }
}

@Composable
private fun AlertHeader(modifier: Modifier) {
    ListItem(modifier = modifier, leadingContent = {
        Icon(
            imageVector = Icons.Default.NotificationsNone,
            contentDescription = "Alert",
            tint = MaterialTheme.colorScheme.primary,
        )
    }, headlineContent = {
        Text(
            text = "Alerts", style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold
            )
        )
    })
}

@Composable
private fun Alert(
    modifier: Modifier, spacing: Dimensions, alert: Alert
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5))
            .background(RadiusAgentAlabaster)
            .padding(spacing.spaceMedium),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
    ) {
        Text(
            modifier = modifier
                .clip(RoundedCornerShape(10))
                .background(RadiusAgentGeneralAccent)
                .padding(spacing.spaceSmall)
                .align(Alignment.End),
            text = alert.event ?: "",
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )

        Text(
            text = alert.headline ?: "",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy hh:mm a")

        Text(
            text = "${
                OffsetDateTime.parse(alert.effective, formatter).toLocalDateTime()
                    .format(outputFormatter)
            } - ${
                OffsetDateTime.parse(alert.expires, formatter).toLocalDateTime()
                    .format(outputFormatter)
            }", style = MaterialTheme.typography.labelMedium
        )

        Text(
            text = alert.desc ?: "", style = MaterialTheme.typography.bodyMedium
        )

    }
}

@Composable
private fun WeatherPreview(
    modifier: Modifier, state: CurrentLocationWeatherDetailScreenContract.State
) {
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

@Composable
private fun CurrentLocation(
    state: CurrentLocationWeatherDetailScreenContract.State, modifier: Modifier, spacing: Dimensions
) {
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

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun WeatherMiscInfo(
    modifier: Modifier, spacing: Dimensions, state: CurrentLocationWeatherDetailScreenContract.State
) {
    val rows = 2
    val columns = 2
    FlowRow(
        modifier = modifier, maxItemsInEachRow = rows
    ) {
        val itemModifier = modifier
            .padding(4.dp)
            .height(80.dp)
            .weight(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(RadiusAgentAlabaster)

        Box(modifier = itemModifier.fillMaxWidth()) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium)
            ) {
                Column(
                    modifier = modifier, horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = modifier, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.BeachAccess,
                            contentDescription = "Precipitation",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = modifier.width(spacing.spaceExtraSmall))
                        Text(
                            text = "Precipitation",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                    Spacer(modifier = modifier.weight(1f))
                    Text(
                        text = "${state.currentLocationWeather?.current?.precipMm} mm",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }

        Box(modifier = itemModifier.fillMaxWidth()) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium)
            ) {
                Column(
                    modifier = modifier, horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = modifier, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Air,
                            contentDescription = "Wind",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = modifier.width(spacing.spaceExtraSmall))
                        Text(
                            text = "Wind", style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                    Spacer(modifier = modifier.weight(1f))
                    Text(
                        text = "${state.currentLocationWeather?.current?.windKph} kmph",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Spacer(modifier = modifier.weight(1f))
                Text(
                    text = "${state.currentLocationWeather?.current?.windDir}",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
        }

        Box(modifier = itemModifier.fillMaxWidth()) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium)
            ) {
                Column(
                    modifier = modifier, horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = modifier, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = "UV Index",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = modifier.width(spacing.spaceExtraSmall))
                        Text(
                            text = "UV Index", style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                    Spacer(modifier = modifier.weight(1f))
                    Text(
                        text = "${state.currentLocationWeather?.current?.uv}",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }

        Box(modifier = itemModifier.fillMaxWidth()) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium)
            ) {
                Column(
                    modifier = modifier, horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = modifier, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = "Sun",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = modifier.width(spacing.spaceExtraSmall))
                        Text(
                            text = "Sun", style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                    Spacer(modifier = modifier.weight(1f))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = modifier.size(12.dp),
                                imageVector = Icons.Default.WbSunny,
                                contentDescription = "Sun",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "${state.currentLocationWeather?.forecast?.forecastday?.first()?.astro?.sunrise}",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                        Spacer(modifier = modifier.weight(1f))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = modifier.size(12.dp),
                                imageVector = Icons.Default.WbSunny,
                                contentDescription = "Sun",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "${state.currentLocationWeather?.forecast?.forecastday?.first()?.astro?.sunset}",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WeeklyForecast(
    modifier: Modifier, spacing: Dimensions, state: CurrentLocationWeatherDetailScreenContract.State
) {
    Card(
        modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(5)
    ) {
        Column(
            modifier = modifier
                .background(RadiusAgentSupportingGray3)
                .fillMaxWidth()
                .padding(spacing.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Calendar Icon",
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "5-day Forecast",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Divider(
                modifier = modifier.fillMaxWidth(),
                thickness = 0.5.dp,
                color = RadiusAgentSupportingGray
            )

            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                state.currentLocationWeather?.forecast?.forecastday?.filterNotNull()?.forEach {
                    Column(
                        modifier = modifier,
                        verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
                    ) {
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            val dayOfWeek = LocalDate.parse(
                                it.date, formatter
                            ).dayOfWeek

                            Text(
                                text = "$dayOfWeek", style = MaterialTheme.typography.labelLarge
                            )

                            Spacer(modifier = modifier.weight(1f))

                            Row(
                                modifier = modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(
                                    spacing.spaceMedium
                                )
                            ) {
                                AsyncImage(
                                    modifier = modifier.size(20.dp),
                                    model = "https:${it.day?.condition?.icon}",
                                    contentDescription = "Current Weather Condition"
                                )

                                Row {
                                    Icon(
                                        modifier = modifier,
                                        imageVector = Icons.Default.ArrowUpward,
                                        contentDescription = "Max Temperature"
                                    )
                                    Text(
                                        text = "${it.day?.maxtempC}°C",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }

                                Row {
                                    Icon(
                                        modifier = modifier,
                                        imageVector = Icons.Default.ArrowDownward,
                                        contentDescription = "Max Temperature"
                                    )
                                    Text(
                                        text = "${it.day?.mintempC}°C",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }

                            }
                        }

                        Divider(
                            modifier = modifier.fillMaxWidth(),
                            thickness = 0.25.dp,
                            color = RadiusAgentSupportingGray
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HourlyForecast(
    modifier: Modifier, spacing: Dimensions, state: CurrentLocationWeatherDetailScreenContract.State
) {
    Card(
        modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(5)
    ) {
        Column(
            modifier = modifier
                .background(RadiusAgentSupportingGray3)
                .fillMaxWidth()
                .padding(spacing.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {
            Text(
                text = state.currentLocationWeather?.current?.condition?.text ?: "",
                style = MaterialTheme.typography.bodyLarge
            )

            Divider(
                modifier = modifier.fillMaxWidth(),
                thickness = 0.5.dp,
                color = RadiusAgentSupportingGray
            )

            LazyRow(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                state.currentLocationWeather?.forecast?.forecastday?.first()?.hour?.filterNotNull()
                    ?.let {
                        items(it) {
                            Column(
                                modifier = modifier.padding(spacing.spaceSmall),
                                verticalArrangement = Arrangement.spacedBy(space = spacing.spaceSmall),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                                val hour = LocalDateTime.parse(it.time, formatter).hour
                                Text(
                                    text = "$hour", style = MaterialTheme.typography.labelLarge
                                )
                                AsyncImage(
                                    modifier = modifier.size(20.dp),
                                    model = "https:${it.condition?.icon}",
                                    contentDescription = "Current Weather Condition"
                                )
                                Text(
                                    text = "${it.tempC}°C",
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }
            }
        }
    }
}