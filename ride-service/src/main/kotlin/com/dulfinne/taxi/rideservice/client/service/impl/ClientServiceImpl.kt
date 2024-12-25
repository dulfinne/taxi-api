package com.dulfinne.taxi.rideservice.client.service.impl

import com.dulfinne.taxi.rideservice.client.DriverClient
import com.dulfinne.taxi.rideservice.client.PassengerClient
import com.dulfinne.taxi.rideservice.client.PaymentClient
import com.dulfinne.taxi.rideservice.client.dto.CanPayByCardResponse
import com.dulfinne.taxi.rideservice.client.dto.DriverResponse
import com.dulfinne.taxi.rideservice.client.dto.PassengerResponse
import com.dulfinne.taxi.rideservice.client.service.ClientService
import com.dulfinne.taxi.rideservice.dto.response.PointResponse
import org.springframework.stereotype.Service

@Service
class ClientServiceImpl(
    val passengerClient: PassengerClient,
    val driverClient: DriverClient,
    val paymentClient: PaymentClient
) : ClientService {

    override fun getPassengerByUsername(username: String): PassengerResponse {
        return passengerClient.getPassengerByUsername(username)
    }

    override fun getDriverByUsername(username: String): DriverResponse {
        return driverClient.getDriverByUsername(username)
    }

    override fun getDriverLocation(username: String): PointResponse {
        return driverClient.getDriverLocationByUsername(username)
    }

    override fun canPayWithCard(username: String): CanPayByCardResponse {
        return paymentClient.canPayWithCard(username)
    }
}