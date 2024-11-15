package com.dulfinne.taxi.rideservice.dto.request

data class LocationRequest(

    val startPosition: PointRequest,

    val endPosition: PointRequest
)