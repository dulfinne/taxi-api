package com.dulfinne.taxi.rideservice.dto.request

import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class PointRequest(

    @field:NotNull
    val latitude: BigDecimal,

    @field:NotNull
    val longitude: BigDecimal
)
