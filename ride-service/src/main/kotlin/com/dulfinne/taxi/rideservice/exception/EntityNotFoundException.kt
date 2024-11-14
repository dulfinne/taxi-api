package com.dulfinne.taxi.rideservice.exception

class EntityNotFoundException(
    val messageKey: String,
    vararg val params: Any
) : RuntimeException()
