package com.dulfinne.taxi.rideservice.client.service.impl

import com.dulfinne.taxi.rideservice.client.DriverClient
import com.dulfinne.taxi.rideservice.client.PassengerClient
import com.dulfinne.taxi.rideservice.client.dto.DriverResponse
import com.dulfinne.taxi.rideservice.client.dto.PassengerResponse
import com.dulfinne.taxi.rideservice.client.service.ClientService
import org.springframework.stereotype.Service

@Service
class ClientServiceImpl(
    val passengerClient: PassengerClient,
    val driverClient: DriverClient
) : ClientService {

    override fun getPassengerByUsername(username: String): PassengerResponse {
        return passengerClient.getPassengerByUsername(username)
    }

    override fun getDriverByUsername(username: String): DriverResponse {
        return driverClient.getDriverByUsername(username)
    }
}