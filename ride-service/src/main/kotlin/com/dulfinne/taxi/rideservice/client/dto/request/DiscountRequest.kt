package com.dulfinne.taxi.rideservice.client.dto.request

import java.math.BigDecimal

data class DiscountRequest(
    val username: String,
    val code: String,
    val price: BigDecimal
)
