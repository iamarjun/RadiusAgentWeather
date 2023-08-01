package com.arjun.weather.data.remote.dto

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ForecastdayResult(
    @Json(name = "astro")
    val astro: AstroResult?,
    @Json(name = "date")
    val date: String?,
    @Json(name = "date_epoch")
    val dateEpoch: Int?,
    @Json(name = "day")
    val day: DayResult?,
    @Json(name = "hour")
    val hour: List<HourResult?>?
) : Parcelable