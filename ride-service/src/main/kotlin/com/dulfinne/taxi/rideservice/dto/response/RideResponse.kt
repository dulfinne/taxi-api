package com.dulfinne.taxi.rideservice.dto.response

import java.math.BigDecimal
import java.time.ZonedDateTime

data class RideResponse(
    var id: Long,

    var driverId: Long?,

    var passengerId: Long,

    var price: BigDecimal,

    var startTime: ZonedDateTime?,

    var endTime: ZonedDateTime?,

    var startPosition: PointResponse,

    var endPosition: PointResponse,

    var statusId: Int
)
