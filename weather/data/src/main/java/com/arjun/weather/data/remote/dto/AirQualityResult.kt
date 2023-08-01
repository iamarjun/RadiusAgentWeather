package com.arjun.weather.data.remote.dto

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AirQualityResult(
    @Json(name = "co")
    val co: Double?,
    @Json(name = "gb-defra-index")
    val gbDefraIndex: Int?,
    @Json(name = "no2")
    val no2: Double?,
    @Json(name = "o3")
    val o3: Double?,
    @Json(name = "pm10")
    val pm10: Double?,
    @Json(name = "pm2_5")
    val pm25: Double?,
    @Json(name = "so2")
    val so2: Double?,
    @Json(name = "us-epa-index")
    val usEpaIndex: Int?
) : Parcelable
