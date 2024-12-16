package com.dulfinne.taxi.rideservice.client.dto

import com.dulfinne.taxi.rideservice.model.Payment

data class PassengerResponse(
    val id: Long,
    val username: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val payment: Payment,
    val rideCount: Int,
    val averageRating: Double
)
