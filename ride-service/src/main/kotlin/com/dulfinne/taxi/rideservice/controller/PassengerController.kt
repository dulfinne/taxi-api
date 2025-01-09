package com.dulfinne.taxi.rideservice.controller

import com.dulfinne.taxi.rideservice.client.dto.DriverResponse
import com.dulfinne.taxi.rideservice.dto.request.LocationRequest
import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.CountPriceResponse
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import com.dulfinne.taxi.rideservice.service.PassengerService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/api/v1/rides/passenger")
class PassengerController(val service: PassengerService) {

    @PostMapping("/price")
    fun countPrice(
        @CurrentSecurityContext(expression = "authentication.name") username: String,
        @RequestBody @Valid request: LocationRequest,
        @RequestParam(defaultValue = "") promocode: String
    ): ResponseEntity<CountPriceResponse> {
        val response = service.countPrice(username, request, promocode)
        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun createRide(
        @CurrentSecurityContext(expression = "authentication.name") username: String,
        @RequestBody @Valid request: LocationRequest,
        @RequestParam(defaultValue = "") promocode: String
    ): ResponseEntity<RideResponse> {

        val response = service.createRide(username, request, promocode)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PostMapping("/cancel/{rideId}")
    fun cancelRide(
        @CurrentSecurityContext(expression = "authentication.name") username: String,
        @PathVariable rideId: Long
    ): ResponseEntity<Void> {

        service.cancelRide(rideId, username)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/rate/{rideId}")
    fun rateDriver(
        @CurrentSecurityContext(expression = "authentication.name") username: String,
        @PathVariable rideId: Long,
        @RequestBody @Valid request: RatingRequest
    ): ResponseEntity<Void> {

        service.rateDriver(rideId, username, request)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/rides")
    fun getAllPassengerRides(
        @CurrentSecurityContext(expression = "authentication.name") username: String,
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int,
        @RequestParam(value = "sort", defaultValue = "id") sortField: String
    ): ResponseEntity<Page<RideResponse>> {

        val ridesResponsePage = service.getAllPassengerRides(username, offset, limit, sortField)
        return ResponseEntity.ok(ridesResponsePage)
    }

    @GetMapping("/rides/{rideId}")
    fun getRideById(
        @CurrentSecurityContext(expression = "authentication.name") username: String,
        @PathVariable rideId: Long
    ): ResponseEntity<RideResponse> {

        val response = service.getRideById(username, rideId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/rides/{rideId}/driver-profile")
    fun getPassengerProfile(
        @CurrentSecurityContext(expression = "authentication.name") username: String,
        @PathVariable rideId: Long
    ): ResponseEntity<DriverResponse> {

        val response = service.getDriverProfile(username, rideId)
        return ResponseEntity.ok(response)
    }
}