package com.dulfinne.taxi.rideservice.dto.response

import java.math.BigDecimal

data class CountPriceResponse(
    val startPosition: PointResponse,
    val endPosition: PointResponse,
    val price: BigDecimal
)
