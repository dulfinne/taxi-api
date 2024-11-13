package com.dulfinne.taxi.rideservice.exception

import org.springframework.http.HttpStatus

data class ErrorResponse(
    val status: HttpStatus,
    val message: String
)