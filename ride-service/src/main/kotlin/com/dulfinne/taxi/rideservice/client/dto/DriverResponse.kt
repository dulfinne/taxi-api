package com.dulfinne.taxi.rideservice.client.dto

class DriverResponse(
    val id: Long,
    val username: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val experience: String,
    val car: CarResponse?,
    val averageRating: Double
)