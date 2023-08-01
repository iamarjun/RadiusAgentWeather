package com.arjun.weather.data.remote.dto

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AlertsResult(
    @Json(name = "alert")
    val alert: List<String?>?
) : Parcelable
