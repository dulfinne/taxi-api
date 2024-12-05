package com.dulfinne.taxi.rideservice.client.service

import com.dulfinne.taxi.rideservice.client.dto.DriverResponse
import com.dulfinne.taxi.rideservice.client.dto.PassengerResponse

interface ClientService {
    fun getPassengerByUsername(username: String): PassengerResponse
    fun getDriverByUsername(username: String): DriverResponse
}