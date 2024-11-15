package com.dulfinne.taxi.rideservice.service

import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import org.springframework.data.domain.Page

interface DriverService {
    fun getAvailableRides(offset: Int, limit: Int, sortField: String): Page<RideResponse>
    fun acceptRide(rideId: Long, driverId: Long): RideResponse
    fun startRide(rideId: Long, driverId: Long): RideResponse
    fun finishRide(rideId: Long, driverId: Long): RideResponse
    fun ratePassenger(rideId: Long, driverId: Long, request: RatingRequest)
    fun getAllDriverRides(driverId:Long, offset: Int, limit: Int, sortField: String): Page<RideResponse>
    fun getRideById(driverId: Long, rideId: Long): RideResponse
}