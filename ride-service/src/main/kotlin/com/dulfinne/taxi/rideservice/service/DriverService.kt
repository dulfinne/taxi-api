package com.dulfinne.taxi.rideservice.service

import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.AvailableRideResponse
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import org.springframework.data.domain.Page

interface DriverService {
    fun getAvailableRides(username: String, radius: Int): List<AvailableRideResponse>
    fun acceptRide(rideId: Long, driverUsername: String): RideResponse
    fun startRide(rideId: Long, driverUsername: String): RideResponse
    fun finishRide(rideId: Long, driverUsername: String): RideResponse
    fun ratePassenger(rideId: Long, driverUsername: String, request: RatingRequest)
    fun getAllDriverRides(driverUsername: String, offset: Int, limit: Int, sortField: String): Page<RideResponse>
    fun getRideById(driverUsername: String, rideId: Long): RideResponse
}