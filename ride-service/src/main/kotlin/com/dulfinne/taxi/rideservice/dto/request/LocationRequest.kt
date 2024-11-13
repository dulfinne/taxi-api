package com.dulfinne.taxi.rideservice.dto.request

import com.dulfinne.taxi.rideservice.util.ValidationKeys
import jakarta.validation.constraints.NotNull

data class LocationRequest (

    @field:NotNull(message = ValidationKeys.START_POSITION_REQUIRED)
    val startPosition: PointRequest,

    @field:NotNull(message = ValidationKeys.END_POSITION_REQUIRED)
    val endPosition: PointRequest
)