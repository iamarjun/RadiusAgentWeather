package com.arjun.weather.data.mapper

import com.arjun.weather.data.remote.dto.LocationResult
import com.arjun.weather.domain.model.Location

object DomainMapper {
    fun LocationResult.toLocation() = Location(
        country = country,
        id = id,
        lat = lat,
        lon = lon,
        name = name,
        region = region,
        url = url
    )
}