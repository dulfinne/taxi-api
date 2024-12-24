package com.dulfinne.taxi.rideservice.controller

import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.AvailableRideResponse
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import com.dulfinne.taxi.rideservice.service.DriverService
import com.dulfinne.taxi.rideservice.util.TokenConstants
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/rides/driver")
class DriverController(val service: DriverService) {

    @GetMapping("/available-rides")
    fun getAvailableRides(
        @CurrentSecurityContext(expression="authentication.name") username: String,
        @RequestParam(value = "radius", defaultValue = "700") radius: Int,
    ): ResponseEntity<List<AvailableRideResponse>> {

        val ridesResponsePage = service.getAvailableRides(username, radius)
        return ResponseEntity.ok(ridesResponsePage)
    }

    @PostMapping("/accept/{rideId}")
    fun acceptRide(
        principal: Principal,
        @PathVariable("rideId") rideId: Long
    ): ResponseEntity<RideResponse> {

        val response = service.acceptRide(rideId, getUsername(principal))
        return ResponseEntity.ok(response)
    }

    @PostMapping("/start/{rideId}")
    fun startRide(
        principal: Principal,
        @PathVariable("rideId") rideId: Long
    ): ResponseEntity<RideResponse> {

        val response = service.startRide(rideId, getUsername(principal))
        return ResponseEntity.ok(response)
    }

    @PostMapping("/finish/{rideId}")
    fun finishRide(
        principal: Principal,
        @PathVariable("rideId") rideId: Long
    ): ResponseEntity<RideResponse> {

        val response = service.finishRide(rideId, getUsername(principal))
        return ResponseEntity.ok(response)
    }

    @PostMapping("/rate/{rideId}")
    fun ratePassenger(
        principal: Principal,
        @PathVariable rideId: Long,
        @RequestBody @Valid request: RatingRequest
    ): ResponseEntity<Void> {

        service.ratePassenger(rideId, getUsername(principal), request)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/rides")
    fun getAllDriverRides(
        principal: Principal,
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int,
        @RequestParam(value = "sort", defaultValue = "id") sortField: String
    ): ResponseEntity<Page<RideResponse>> {

        val ridesResponsePage = service.getAllDriverRides(getUsername(principal), offset, limit, sortField)
        return ResponseEntity.ok(ridesResponsePage)
    }

    @GetMapping("/rides/{rideId}")
    fun getRideById(
        principal: Principal,
        @PathVariable rideId: Long
    ): ResponseEntity<RideResponse> {

        val response = service.getRideById(getUsername(principal), rideId)
        return ResponseEntity.ok(response)
    }

    fun getUsername(principal: Principal): String {
        val authentication = principal as Authentication
        val jwt = authentication.principal as Jwt
        return jwt.getClaim(TokenConstants.USERNAME_CLAIM)
    }
}