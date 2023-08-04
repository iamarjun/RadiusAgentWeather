package com.arjun.weather.presentation.detail

import androidx.lifecycle.viewModelScope
import com.arjun.core.state_management.BaseViewModel
import com.arjun.weather.domain.model.CurrentLocationWeather
import com.arjun.weather.domain.usecases.CacheLocation
import com.arjun.weather.domain.usecases.GetWeatherInfo
import com.arjun.weather.domain.usecases.LocationExistsInCache
import com.arjun.weather.domain.usecases.RemoveLocationFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentLocationWeatherDetailScreenViewModel @Inject constructor(
    private val getWeatherInfo: GetWeatherInfo,
    private val cacheLocation: CacheLocation,
    private val removeLocationFromCache: RemoveLocationFromCache,
    private val locationExistsInCache: LocationExistsInCache,
) : BaseViewModel<CurrentLocationWeatherDetailScreenContract.Event, CurrentLocationWeatherDetailScreenContract.State, CurrentLocationWeatherDetailScreenContract.Effect>() {
    override fun createInitialState(): CurrentLocationWeatherDetailScreenContract.State {
        return CurrentLocationWeatherDetailScreenContract.State()
    }

    override fun handleEvent(event: CurrentLocationWeatherDetailScreenContract.Event) {
        when (event) {
            CurrentLocationWeatherDetailScreenContract.Event.OnBackPress -> {
                setEffect {
                    CurrentLocationWeatherDetailScreenContract.Effect.NavigateUp
                }
            }

            is CurrentLocationWeatherDetailScreenContract.Event.SaveToDb -> {
                savedToLocalCache(event.currentLocationWeather)
            }

            is CurrentLocationWeatherDetailScreenContract.Event.DeleteFromDb -> {
                removeFromLocalCache(event.latLon)
            }
        }
    }

    private fun removeFromLocalCache(latLon: String?) {
        viewModelScope.launch {
            latLon?.let {
                removeLocationFromCache(it)
                    .onSuccess {
                        setState {
                            copy(isSaved = it)
                        }
                    }
                    .onFailure {
                        setEffect {
                            CurrentLocationWeatherDetailScreenContract.Effect.ShowToast(
                                it.localizedMessage ?: "Something went wrong"
                            )
                        }
                    }
            }
        }
    }

    private fun savedToLocalCache(currentLocationWeather: CurrentLocationWeather?) {
        viewModelScope.launch {
            currentLocationWeather?.let {
                cacheLocation(it)
                    .onSuccess {
                        setState {
                            copy(
                                isSaved = it
                            )
                        }
                    }
                    .onFailure {
                        setEffect {
                            CurrentLocationWeatherDetailScreenContract.Effect.ShowToast(
                                it.localizedMessage ?: "Something went wrong"
                            )
                        }
                    }
            }
        }
    }

    fun getCurrentLocationWeather(slug: String) {
        viewModelScope.launch {
            setState {
                copy(isLoading = true)
            }

            getWeatherInfo(slug)
                .onSuccess {
                    println("Weather $it")
                    checkIfCurrentLocationExists(it)
                    setState {
                        copy(
                            isLoading = false,
                            currentLocationWeather = it
                        )
                    }
                }
                .onFailure {
                    setState {
                        copy(
                            isLoading = false,
                        )
                    }
                    setEffect {
                        CurrentLocationWeatherDetailScreenContract.Effect.ShowToast(
                            it.localizedMessage ?: "Something went wrong"
                        )
                    }
                }
        }
    }

    private suspend fun checkIfCurrentLocationExists(it: CurrentLocationWeather) {
        locationExistsInCache(it.latlon)
            .onSuccess {
                setState {
                    copy(
                        isSaved = it,
                    )
                }
            }
            .onFailure {
                setEffect {
                    CurrentLocationWeatherDetailScreenContract.Effect.ShowToast(
                        it.localizedMessage ?: "Something went wrong"
                    )
                }
            }
    }
}