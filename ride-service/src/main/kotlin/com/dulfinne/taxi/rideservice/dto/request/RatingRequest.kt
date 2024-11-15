package com.dulfinne.taxi.rideservice.dto.request

import com.dulfinne.taxi.rideservice.util.ValidationKeys
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.hibernate.validator.constraints.Length

data class RatingRequest(

    @field:Min(value = 1, message = ValidationKeys.INVALID_RATING_RANGE)
    @field:Max(value = 5, message = ValidationKeys.INVALID_RATING_RANGE)
    val rating: Int,

    @field:Length(max = 255, message = ValidationKeys.INVALID_FEEDBACK_LENGTH)
    val feedback: String
)
