package com.arjun.weather.data.remote.dto

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ConditionResult(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "icon")
    val icon: String?,
    @Json(name = "text")
    val text: String?
) : Parcelable