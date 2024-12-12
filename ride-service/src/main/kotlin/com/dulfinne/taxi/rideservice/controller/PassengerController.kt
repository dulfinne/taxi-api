package com.dulfinne.taxi.rideservice.controller

import com.dulfinne.taxi.rideservice.client.dto.DriverResponse
import com.dulfinne.taxi.rideservice.dto.request.LocationRequest
import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.CountPriceResponse
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import com.dulfinne.taxi.rideservice.service.PassengerService
import com.dulfinne.taxi.rideservice.util.TokenConstants
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
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
@RequestMapping("/api/v1/rides/passenger")
class PassengerController(val service: PassengerService) {

    @PostMapping("/price")
    fun countPrice(@RequestBody @Valid request: LocationRequest): ResponseEntity<CountPriceResponse> {
        val response = service.countPrice(request)
        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun createRide(
        principal: Principal,
        @RequestBody @Valid request: LocationRequest
    ): ResponseEntity<RideResponse> {

        val response = service.createRide(getUsername(principal), request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PostMapping("/cancel/{rideId}")
    fun cancelRide(
        principal: Principal, @PathVariable rideId: Long
    ): ResponseEntity<Void> {

        service.cancelRide(rideId, getUsername(principal))
        return ResponseEntity.ok().build()
    }

    @PostMapping("/rate/{rideId}")
    fun rateDriver(
        principal: Principal,
        @PathVariable rideId: Long,
        @RequestBody @Valid request: RatingRequest
    ): ResponseEntity<Void> {

        service.rateDriver(rideId, getUsername(principal), request)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/rides")
    fun getAllPassengerRides(
        principal: Principal,
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int,
        @RequestParam(value = "sort", defaultValue = "id") sortField: String
    ): ResponseEntity<Page<RideResponse>> {

        val ridesResponsePage = service.getAllPassengerRides(getUsername(principal), offset, limit, sortField)
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

    @GetMapping("/rides/{rideId}/driver-profile")
    fun getPassengerProfile(
        principal: Principal,
        @PathVariable rideId: Long
    ): ResponseEntity<DriverResponse> {

        val response = service.getDriverProfile(getUsername(principal), rideId)
        return ResponseEntity.ok(response)
    }

    fun getUsername(principal: Principal): String {
        val authentication = principal as Authentication
        val jwt = authentication.principal as Jwt
        return jwt.getClaim(TokenConstants.USERNAME_CLAIM)
    }
}