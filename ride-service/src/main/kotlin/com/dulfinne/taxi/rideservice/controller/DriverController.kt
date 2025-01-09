package com.dulfinne.taxi.rideservice.controller

import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.AvailableRideResponse
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import com.dulfinne.taxi.rideservice.service.DriverService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
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
@RequestMapping("/api/v1/rides/driver")
class DriverController(val service: DriverService) {

    @GetMapping("/available-rides")
    fun getAvailableRides(
        @CurrentSecurityContext(expression = "authentication.name") username: String,
        @RequestParam(value = "radius", defaultValue = "700") radius: Int,
    ): ResponseEntity<List<AvailableRideResponse>> {

        val ridesResponsePage = service.getAvailableRides(username, radius)
        return ResponseEntity.ok(ridesResponsePage)
    }

    @PostMapping("/accept/{rideId}")
    fun acceptRide(
        @CurrentSecurityContext(expression = "authentication.name") username: String,
        @PathVariable("rideId") rideId: Long
    ): ResponseEntity<RideResponse> {

        val response = service.acceptRide(rideId, username)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/start/{rideId}")
    fun startRide(
        @CurrentSecurityContext(expression = "authentication.name") username: String,
        @PathVariable("rideId") rideId: Long
    ): ResponseEntity<RideResponse> {

        val response = service.startRide(rideId, username)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/finish/{rideId}")
    fun finishRide(
        @CurrentSecurityContext(expression = "authentication.name") username: String,
        @PathVariable("rideId") rideId: Long
    ): ResponseEntity<RideResponse> {

        val response = service.finishRide(rideId, username)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/rate/{rideId}")
    fun ratePassenger(
        @CurrentSecurityContext(expression = "authentication.name") username: String,
        @PathVariable rideId: Long,
        @RequestBody @Valid request: RatingRequest
    ): ResponseEntity<Void> {

        service.ratePassenger(rideId, username, request)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/rides")
    fun getAllDriverRides(
        @CurrentSecurityContext(expression = "authentication.name") username: String,
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int,
        @RequestParam(value = "sort", defaultValue = "id") sortField: String
    ): ResponseEntity<Page<RideResponse>> {

        val ridesResponsePage = service.getAllDriverRides(username, offset, limit, sortField)
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
}