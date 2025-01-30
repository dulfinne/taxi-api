package com.dulfinne.taxi.rideservice.client.service.impl

import com.dulfinne.taxi.rideservice.client.DriverClient
import com.dulfinne.taxi.rideservice.client.PassengerClient
import com.dulfinne.taxi.rideservice.client.PaymentClient
import com.dulfinne.taxi.rideservice.client.PromocodeClient
import com.dulfinne.taxi.rideservice.client.dto.CanPayByCardResponse
import com.dulfinne.taxi.rideservice.client.dto.DiscountResponse
import com.dulfinne.taxi.rideservice.client.dto.DriverResponse
import com.dulfinne.taxi.rideservice.client.dto.PassengerResponse
import com.dulfinne.taxi.rideservice.client.dto.request.DiscountRequest
import com.dulfinne.taxi.rideservice.client.service.ClientService
import com.dulfinne.taxi.rideservice.dto.response.PointResponse
import com.dulfinne.taxi.rideservice.exception.ServiceNotAvailableException
import com.dulfinne.taxi.rideservice.util.ExceptionKeys
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
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

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ClientServiceImpl::class.java)
    }

    @CircuitBreaker(name = "passenger-service", fallbackMethod = "fallBackPassengerService")
    override fun getPassengerByUsername(username: String): PassengerResponse {
        log.info("Getting passenger by username. Started. Username = $username")
        return passengerClient.getPassengerByUsername(username)
    }

    @CircuitBreaker(name = "driver-service", fallbackMethod = "fallBackDriverService")
    override fun getDriverByUsername(username: String): DriverResponse {
        log.info("Getting driver by username. Started. Username = $username")
        return driverClient.getDriverByUsername(username)
    }

    @CircuitBreaker(name = "driver-service", fallbackMethod = "fallBackDriverServiceLocation")
    override fun getDriverLocation(username: String): PointResponse {
        log.info("Getting driver's location. Started. Username = $username")
        return driverClient.getDriverLocationByUsername(username)
    }

    @CircuitBreaker(name = "payment-service", fallbackMethod = "fallBackPaymentService")
    override fun canPayWithCard(username: String): CanPayByCardResponse {
        log.info("Getting card payment availability. Started. Username = $username")
        return paymentClient.canPayWithCard(username)
    }

    @CircuitBreaker(name = "promocode-service", fallbackMethod = "fallBackPromocodeService")
    override fun getDiscount(request: DiscountRequest): DiscountResponse {
        log.info("Getting discount. Started. Username = ${request.username}")
        return promocodeClient.getDiscount(request)
    }

    private fun fallBackPassengerService(ex: Exception): PassengerResponse {
        log.info("Passenger service is not available. Fallback method was called.")
        throw ServiceNotAvailableException(ExceptionKeys.PASSENGER_SERVICE_NOT_AVAILABLE)
    }

    private fun fallBackDriverService(ex: Exception): DriverResponse {
        log.info("Driver service is not available. Fallback method was called.")
        throw ServiceNotAvailableException(ExceptionKeys.DRIVER_SERVICE_NOT_AVAILABLE)
    }

    private fun fallBackDriverServiceLocation(ex: Exception): PointResponse {
        log.info("Driver service is not available. Fallback method was called.")
        throw ServiceNotAvailableException(ExceptionKeys.DRIVER_SERVICE_NOT_AVAILABLE)
    }

    private fun fallBackPaymentService(ex: Exception): CanPayByCardResponse {
        log.info("Payment service is not available. Fallback method was called.")
        throw ServiceNotAvailableException(ExceptionKeys.PAYMENT_SERVICE_NOT_AVAILABLE)
    }

    private fun fallBackPromocodeService(ex: Exception): DiscountResponse {
        log.info("Promocode service is not available. Fallback method was called.")
        throw ServiceNotAvailableException(ExceptionKeys.PROMOCODE_SERVICE_NOT_AVAILABLE)
    }
}