package com.arjun.radiusagentweather.navigation

import androidx.navigation.NavController
import com.arjun.weather.presentation.destinations.CurrentLocationWeatherDetailScreenDestination
import com.arjun.weather.presentation.destinations.WeatherHomeScreenDestination
import com.arjun.weather.presentation.detail.CurrentLocationWeatherDetailScreenNavigator
import com.arjun.weather.presentation.home.WeatherHomeScreenNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DestinationSpec

class CoreFeatureNavigator(
    private val currentDestination: DestinationSpec<*>,
    private val navController: NavController
) : WeatherHomeScreenNavigator, CurrentLocationWeatherDetailScreenNavigator {

    override fun navigateToWeatherDetailScreen() {
        val nextDirection = when (currentDestination) {
            WeatherHomeScreenDestination -> CurrentLocationWeatherDetailScreenDestination
            else -> throw RuntimeException("Destination Route Not Found")
        }

        navController.navigate(nextDirection)
    }

    override fun navigateUp() {
        navController.navigateUp()
    }

}