package com.dulfinne.taxi.rideservice.dto.response

import java.math.BigDecimal

data class PointResponse(
    val latitude: BigDecimal,
    val longitude: BigDecimal
)
