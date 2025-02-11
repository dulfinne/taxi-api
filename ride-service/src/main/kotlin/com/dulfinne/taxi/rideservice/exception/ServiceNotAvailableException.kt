package com.dulfinne.taxi.rideservice.exception

class ServiceNotAvailableException(
    val messageKey: String
) : RuntimeException()