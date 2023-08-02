package com.arjun.weather.data.remote.dto

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CurrentLocationWeatherResult(
    @Json(name = "location")
    val location: LocationResult,
    @Json(name = "current")
    val current: WeatherResult,
    @Json(name = "alerts")
    val alerts: AlertsResult?,
    @Json(name = "forecast")
    val forecast: ForecastResult?
) : Parcelable
