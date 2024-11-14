package com.dulfinne.taxi.rideservice.dto.request

import com.dulfinne.taxi.rideservice.util.ValidationKeys
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class PointRequest(

    @field:NotNull(message = ValidationKeys.LATITUDE_REQUIRED)
    val latitude: BigDecimal?,

    @field:NotNull(message = ValidationKeys.LONGITUDE_REQUIRED)
    val longitude: BigDecimal?
)
