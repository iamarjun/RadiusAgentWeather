package com.arjun.weather.presentation.home

import androidx.lifecycle.viewModelScope
import com.arjun.core.state_management.BaseViewModel
import com.arjun.weather.domain.location.LocationTracker
import com.arjun.weather.domain.usecases.GetAllLocation
import com.arjun.weather.domain.usecases.RemoveLocationFromCache
import com.arjun.weather.domain.usecases.SearchLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHomeScreenViewModel @Inject constructor(
    private val searchLocation: SearchLocation,
    private val locationTracker: LocationTracker,
    private val getAllLocation: GetAllLocation,
    private val removeLocationFromCache: RemoveLocationFromCache,
) : BaseViewModel<WeatherHomeScreenContract.Event, WeatherHomeScreenContract.State, WeatherHomeScreenContract.Effect>() {
    override fun createInitialState(): WeatherHomeScreenContract.State {
        return WeatherHomeScreenContract.State()
    }

    override fun handleEvent(event: WeatherHomeScreenContract.Event) {
        when (event) {
            is WeatherHomeScreenContract.Event.OnQueryChange -> {
                setState { copy(query = event.query) }
                debounceSearch(event)
            }

            WeatherHomeScreenContract.Event.OnSearch -> {
                executeSearch()
            }

            is WeatherHomeScreenContract.Event.OnSearchFocusChange -> {
                setState {
                    copy(
                        showHint = !event.isFocused && currentState.query.isNullOrBlank()
                    )
                }
            }

            is WeatherHomeScreenContract.Event.OnSearchLocationResultClick -> {
                setEffect {
                    WeatherHomeScreenContract.Effect.NavigateToCurrentLocationWeatherDetailScreen(
                        slug = event.slug
                    )
                }
            }

            WeatherHomeScreenContract.Event.OnUseCurrentLocation -> {
                getCurrentLocation()
            }

            WeatherHomeScreenContract.Event.OnClearSearch -> {
                setState {
                    copy(
                        query = null,
                        searchLocationResult = emptyList(),
                        isSearching = false
                    )
                }
            }

            WeatherHomeScreenContract.Event.GetSavedLocations -> {
                getSavedLocation()
            }

            is WeatherHomeScreenContract.Event.OnItemDismissed -> {
                removeFromLocalCache(event.item.latlon)
            }
        }
    }

    private fun debounceSearch(event: WeatherHomeScreenContract.Event.OnQueryChange) {
        viewModelScope.launch {
            println("Debounce ${event.query}")
            delay(300)
            if (event.query.length > 3)
                executeSearch()
        }
    }

    private fun removeFromLocalCache(latLon: String?) {
        viewModelScope.launch {
            latLon?.let {
                removeLocationFromCache(it)
                    .onSuccess {

                    }
                    .onFailure {
                        setEffect {
                            WeatherHomeScreenContract.Effect.ShowToast(
                                it.localizedMessage ?: "Something went wrong"
                            )
                        }
                    }
            }
        }
    }

    private fun getSavedLocation() {
        viewModelScope.launch {
            getAllLocation().collectLatest {
                setState {
                    copy(savedLocations = it)
                }
            }

        }
    }

    fun getCurrentLocation() {
        viewModelScope.launch {
            val currentLocation = locationTracker.getCurrentLocation()
            println("Location: $currentLocation")
            setState {
                copy(
                    currentLocation = currentLocation
                )
            }
            setEffect {
                WeatherHomeScreenContract.Effect.NavigateToCurrentLocationWeatherDetailScreen(
                    slug = "${currentLocation?.latitude},${currentLocation?.longitude}"
                )
            }
        }
    }

    private fun executeSearch() {
        viewModelScope.launch {
            setState {
                copy(
                    isSearching = true,
                )
            }

            currentState.query?.let { query ->
                searchCurrentLocation(query)
            }
        }
    }

    private suspend fun searchCurrentLocation(query: String) {
        searchLocation.invoke(query)
            .onSuccess {
                println("Location $it")
                setState {
                    copy(
                        isSearching = false,
                        searchLocationResult = it
                    )
                }
            }
            .onFailure {
                setState {
                    copy(
                        isSearching = false,
                    )
                }
                setEffect {
                    WeatherHomeScreenContract.Effect.ShowToast(
                        it.localizedMessage ?: "Something went wrong"
                    )
                }
            }
    }
}