package com.arjun.weather.presentation.detail

import androidx.lifecycle.viewModelScope
import com.arjun.core.state_management.BaseViewModel
import com.arjun.weather.domain.usecases.GetWeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentLocationWeatherDetailScreenViewModel @Inject constructor(
    private val getWeatherInfo: GetWeatherInfo
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
}