package com.dulfinne.taxi.rideservice.service

import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import org.springframework.data.domain.Page

interface RideService {
    fun getAllRides(offset: Int, limit: Int, sortField: String): Page<RideResponse>
    fun getRideById(rideId: Long): RideResponse
    fun deleteRideById(rideId: Long)
}