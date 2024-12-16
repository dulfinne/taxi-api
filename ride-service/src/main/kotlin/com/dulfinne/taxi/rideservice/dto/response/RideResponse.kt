package com.dulfinne.taxi.rideservice.dto.response

import com.dulfinne.taxi.rideservice.model.Payment
import com.dulfinne.taxi.rideservice.model.RideStatus
import java.math.BigDecimal
import java.time.ZonedDateTime

data class RideResponse(
    var id: Long,

    var driverUsername: String?,

    var passengerUsername: String,

    var price: BigDecimal,

    var payment: Payment,

    var startTime: ZonedDateTime?,

    var endTime: ZonedDateTime?,

    var startPosition: PointResponse,

    var endPosition: PointResponse,

    var status: RideStatus
)
