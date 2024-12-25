package com.dulfinne.taxi.rideservice.client.service

import com.dulfinne.taxi.rideservice.client.dto.CanPayByCardResponse
import com.dulfinne.taxi.rideservice.client.dto.DriverResponse
import com.dulfinne.taxi.rideservice.client.dto.PassengerResponse
import com.dulfinne.taxi.rideservice.dto.response.PointResponse

interface ClientService {
    fun getPassengerByUsername(username: String): PassengerResponse
    fun getDriverByUsername(username: String): DriverResponse
    fun getDriverLocation(username: String): PointResponse
    fun canPayWithCard(username: String): CanPayByCardResponse
}