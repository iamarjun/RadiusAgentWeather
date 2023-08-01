package com.arjun.radiusagentweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.arjun.core_ui.theme.RadiusAgentWeatherTheme
import com.arjun.radiusagentweather.navigation.CoreFeatureNavigator
import com.arjun.radiusagentweather.navigation.RootNavGraph
import com.arjun.weather.presentation.WeatherNavGraph
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RadiusAgentWeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val snackbarHostState = remember { SnackbarHostState() }
                    val modifier = Modifier
                    DestinationsNavHost(
                        navController = navController,
                        navGraph = RootNavGraph,
                        startRoute = WeatherNavGraph,
                        dependenciesContainerBuilder = {
                            dependency(modifier)
                            dependency(CoreFeatureNavigator(destination, navController))
                            dependency(snackbarHostState)
                        }
                    )
                }
            }
        }
    }
}
