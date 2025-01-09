package com.dulfinne.taxi.rideservice.dto.response

import com.dulfinne.taxi.rideservice.model.RideStatus

data class AvailableRideResponse(
    var id: Long,

    var passengerUsername: String,

    var startPosition: PointResponse,

    var endPosition: PointResponse,

    var status: RideStatus
)
