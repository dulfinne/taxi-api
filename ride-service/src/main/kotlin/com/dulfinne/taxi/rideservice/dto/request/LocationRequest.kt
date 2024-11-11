package com.dulfinne.taxi.rideservice.dto.request

import jakarta.validation.constraints.NotNull

data class LocationRequest (

    @field:NotNull(message = "Start position can't be empty")
    val startPosition: PointRequest,

    @field:NotNull(message = "End position can't be empty")
    val endPosition: PointRequest
)