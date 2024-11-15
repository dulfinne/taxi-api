package com.dulfinne.taxi.rideservice.dto.request

import java.math.BigDecimal

data class PointRequest(

    val latitude: BigDecimal,

    val longitude: BigDecimal
)
