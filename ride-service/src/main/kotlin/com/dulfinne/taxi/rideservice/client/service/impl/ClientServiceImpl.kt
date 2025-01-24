package com.dulfinne.taxi.rideservice.client.service.impl

import com.dulfinne.taxi.rideservice.client.DriverClient
import com.dulfinne.taxi.rideservice.client.PassengerClient
import com.dulfinne.taxi.rideservice.client.PaymentClient
import com.dulfinne.taxi.rideservice.client.PromocodeClient
import com.dulfinne.taxi.rideservice.client.dto.request.DiscountRequest
import com.dulfinne.taxi.rideservice.client.dto.CanPayByCardResponse
import com.dulfinne.taxi.rideservice.client.dto.DiscountResponse
import com.dulfinne.taxi.rideservice.client.dto.DriverResponse
import com.dulfinne.taxi.rideservice.client.dto.PassengerResponse
import com.dulfinne.taxi.rideservice.client.service.ClientService
import com.dulfinne.taxi.rideservice.dto.response.PointResponse
import com.dulfinne.taxi.rideservice.kafka.service.KafkaProducerService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ClientServiceImpl(
    val passengerClient: PassengerClient,
    val driverClient: DriverClient,
    val paymentClient: PaymentClient,
    val promocodeClient: PromocodeClient
) : ClientService {
    private val log: Logger = LoggerFactory.getLogger(KafkaProducerService::class.java)

    override fun getPassengerByUsername(username: String): PassengerResponse {
        log.info("Getting passenger by username. Started. Username = $username")
        return passengerClient.getPassengerByUsername(username)
    }

    override fun getDriverByUsername(username: String): DriverResponse {
        log.info("Getting driver by username. Started. Username = $username")
        return driverClient.getDriverByUsername(username)
    }

    override fun getDriverLocation(username: String): PointResponse {
        log.info("Getting driver's location. Started. Username = $username")
        return driverClient.getDriverLocationByUsername(username)
    }

    override fun canPayWithCard(username: String): CanPayByCardResponse {
        log.info("Getting card payment availability. Started. Username = $username")
        return paymentClient.canPayWithCard(username)
    }

    override fun getDiscount(request: DiscountRequest): DiscountResponse {
        log.info("Getting discount. Started. Username = ${request.username}")
        return promocodeClient.getDiscount(request)
    }
}