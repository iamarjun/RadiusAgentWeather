package com.arjun.weather.domain.model

data class Location(
    val country: String?,
    val id: Int?,
    val lat: Double?,
    val lon: Double?,
    val name: String?,
    val region: String?,
    val url: String?
)