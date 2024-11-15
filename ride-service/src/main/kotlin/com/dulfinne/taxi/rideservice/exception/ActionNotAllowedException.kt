package com.dulfinne.taxi.rideservice.exception

class ActionNotAllowedException(
    val messageKey: String,
    vararg val params: Any
) : RuntimeException()