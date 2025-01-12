package com.dulfinne.taxi.rideservice.exception

class ClientException(
    val code: Int,
    val info: String
) : RuntimeException()
