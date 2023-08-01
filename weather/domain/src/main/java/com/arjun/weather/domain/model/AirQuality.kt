package com.arjun.weather.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AirQuality(
    val co: Double?,
    val gbDefraIndex: Int?,
    val no2: Double?,
    val o3: Double?,
    val pm10: Double?,
    val pm25: Double?,
    val so2: Double?,
    val usEpaIndex: Int?
) : Parcelable
