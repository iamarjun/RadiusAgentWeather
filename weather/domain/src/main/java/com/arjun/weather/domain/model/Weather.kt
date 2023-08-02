package com.arjun.weather.domain.model


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Weather(
    
    val condition: Condition?,
    val feelslikeC: Double?,
    val lastUpdated: String?,
    val precipMm: Double?,
    val tempC: Double?,
    val uv: Double?,
    val windDir: String?,
    val windKph: Double?,
) : Parcelable
