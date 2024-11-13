package com.dulfinne.taxi.rideservice.service

import com.dulfinne.taxi.rideservice.dto.request.LocationRequest
import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.CountPriceResponse
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import org.springframework.data.domain.Page

interface PassengerService {
    fun countPrice(request: LocationRequest): CountPriceResponse
    fun createRide(passengerId: Long, request: LocationRequest): RideResponse
    fun cancelRide(rideId: Long, passengerId: Long)
    fun rateDriver(rideId: Long, passengerId: Long, request: RatingRequest)
    fun getAllPassengerRides(passengerId: Long, offset: Int, limit: Int, sortField: String): Page<RideResponse>
    fun getRideById(passengerId: Long, rideId: Long): RideResponse
}