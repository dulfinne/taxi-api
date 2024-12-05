package com.dulfinne.taxi.rideservice.service

import com.dulfinne.taxi.rideservice.client.dto.DriverResponse
import com.dulfinne.taxi.rideservice.dto.request.LocationRequest
import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.CountPriceResponse
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import org.springframework.data.domain.Page

interface PassengerService {
    fun countPrice(request: LocationRequest): CountPriceResponse
    fun createRide(passengerUsername: String, request: LocationRequest): RideResponse
    fun cancelRide(rideId: Long, passengerUsername: String)
    fun rateDriver(rideId: Long, passengerUsername: String, request: RatingRequest)
    fun getAllPassengerRides(passengerUsername: String, offset: Int, limit: Int, sortField: String): Page<RideResponse>
    fun getRideById(passengerUsername: String, rideId: Long): RideResponse
    fun getDriverProfile(passengerUsername: String, rideId: Long): DriverResponse
}