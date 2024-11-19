package com.dulfinne.taxi.rideservice.controller

import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import com.dulfinne.taxi.rideservice.service.DriverService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rides/driver")
class DriverController(val service: DriverService) {

    @GetMapping("/available-rides")
    fun getAvailableRides(
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int,
        @RequestParam(value = "sort", defaultValue = "id") sortField: String
    ): ResponseEntity<Page<RideResponse>> {

        val ridesResponsePage = service.getAvailableRides(offset, limit, sortField)
        return ResponseEntity.ok(ridesResponsePage)
    }

    // TODO: Get {driverUsername} from token
    @PostMapping("/{driverUsername}/accept/{rideId}")
    fun acceptRide(
        @PathVariable("rideId") rideId: Long,
        @PathVariable("driverUsername") driverUsername: String
    ): ResponseEntity<RideResponse> {

        val response = service.acceptRide(rideId, driverUsername)
        return ResponseEntity.ok(response)
    }

    // TODO: Get {driverUsername} from token
    @PostMapping("/{driverUsername}/start/{rideId}")
    fun startRide(
        @PathVariable("rideId") rideId: Long,
        @PathVariable("driverUsername") driverUsername: String
    ): ResponseEntity<RideResponse> {

        val response = service.startRide(rideId, driverUsername)
        return ResponseEntity.ok(response)
    }

    // TODO: Get {driverUsername} from token
    @PostMapping("/{driverUsername}/finish/{rideId}")
    fun finishRide(
        @PathVariable("rideId") rideId: Long,
        @PathVariable("driverUsername") driverUsername: String
    ): ResponseEntity<RideResponse> {

        val response = service.finishRide(rideId, driverUsername)
        return ResponseEntity.ok(response)
    }

    // TODO: Get {driverUsername} from token
    @PostMapping("/{driverUsername}/rate/{rideId}")
    fun ratePassenger(
        @PathVariable rideId: Long,
        @PathVariable driverUsername: String,
        @RequestBody @Valid request: RatingRequest
    ): ResponseEntity<Void> {

        service.ratePassenger(rideId, driverUsername, request)
        return ResponseEntity.ok().build()
    }

    // TODO: Get {driverUsername} from token
    @GetMapping("/{driverUsername}/rides")
    fun getAllDriverRides(
        @PathVariable driverUsername: String,
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int,
        @RequestParam(value = "sort", defaultValue = "id") sortField: String
    ): ResponseEntity<Page<RideResponse>> {

        val ridesResponsePage = service.getAllDriverRides(driverUsername, offset, limit, sortField)
        return ResponseEntity.ok(ridesResponsePage)
    }

    @GetMapping("/{driverUsername}/rides/{rideId}")
    fun getRideById(
        @PathVariable driverUsername: String,
        @PathVariable rideId: Long
    ): ResponseEntity<RideResponse> {

        val response = service.getRideById(driverUsername, rideId)
        return ResponseEntity.ok(response)
    }
}