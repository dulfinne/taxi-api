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

    // TODO: Get {driverId} from token
    @PostMapping("/{driverId}/accept/{rideId}")
    fun acceptRide(
        @PathVariable("rideId") rideId: Long,
        @PathVariable("driverId") driverId: Long
    ): ResponseEntity<RideResponse> {

        val response = service.acceptRide(rideId, driverId)
        return ResponseEntity.ok(response)
    }

    // TODO: Get {driverId} from token
    @PostMapping("/{driverId}/start/{rideId}")
    fun startRide(
        @PathVariable("rideId") rideId: Long,
        @PathVariable("driverId") driverId: Long
    ): ResponseEntity<RideResponse> {

        val response = service.startRide(rideId, driverId)
        return ResponseEntity.ok(response)
    }

    // TODO: Get {driverId} from token
    @PostMapping("/{driverId}/finish/{rideId}")
    fun finishRide(
        @PathVariable("rideId") rideId: Long,
        @PathVariable("driverId") driverId: Long
    ): ResponseEntity<RideResponse> {

        val response = service.finishRide(rideId, driverId)
        return ResponseEntity.ok(response)
    }

    // TODO: Get {driverId} from token
    @PostMapping("/{driverId}/rate/{rideId}")
    fun ratePassenger(
        @PathVariable rideId: Long,
        @PathVariable driverId: Long,
        @RequestBody @Valid request: RatingRequest
    ): ResponseEntity<Void> {

        service.ratePassenger(rideId, driverId, request)
        return ResponseEntity.ok().build()
    }

    // TODO: Get {driverId} from token
    @GetMapping("/{driverId}/rides")
    fun getAllDriverRides(
        @PathVariable driverId: Long,
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int,
        @RequestParam(value = "sort", defaultValue = "id") sortField: String
    ): ResponseEntity<Page<RideResponse>> {

        val ridesResponsePage = service.getAllDriverRides(driverId, offset, limit, sortField)
        return ResponseEntity.ok(ridesResponsePage)
    }

    @GetMapping("/{driverId}/rides/{rideId}")
    fun getRideById(
        @PathVariable driverId: Long,
        @PathVariable rideId: Long
    ): ResponseEntity<RideResponse> {

        val response = service.getRideById(driverId, rideId)
        return ResponseEntity.ok(response)
    }
}