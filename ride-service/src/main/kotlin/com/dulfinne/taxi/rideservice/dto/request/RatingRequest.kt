package com.dulfinne.taxi.rideservice.dto.request

import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length

data class RatingRequest(

    @field:NotNull(message = "Rating can't be empty")
    val rating: Int,

    @field:Length(max = 255, message = "Rating must be under 255 letters")
    val feedback: String
)
