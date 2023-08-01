package com.arjun.weather.data.remote.dto


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize



@Keep
@Parcelize
data class ForecastResult(
    @Json(name = "forecastday")
    val forecastday: List<ForecastdayResult?>?
) : Parcelable
