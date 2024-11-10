package com.dulfinne.taxi.rideservice.controller

import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
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
class DriverController {

    @GetMapping("/available-rides")
    fun getAvailableRides(
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int,
        @RequestParam(value = "sort", defaultValue = "id") sortField: String
    ): ResponseEntity<Page<RideResponse>> {

        // TODO: Impl services
        val ridesResponsePage: Page<RideResponse>? = null
        return ResponseEntity.ok(ridesResponsePage)
    }

    // TODO: Get {driverId} from token
    @PostMapping("/{rideId}/accept/{driverId}")
    fun acceptRide(
        @PathVariable("rideId") rideId: Long,
        @PathVariable("driverId") driverId: Long
    ): ResponseEntity<RideResponse> {

        // TODO: Impl services
        val response: RideResponse? = null
        return ResponseEntity.ok(response)
    }

    // TODO: Get {driverId} from token
    @PostMapping("/{rideId}/start/{driverId}")
    fun startRide(
        @PathVariable("rideId") rideId: Long,
        @PathVariable("driverId") driverId: Long
    ): ResponseEntity<RideResponse> {

        // TODO: Impl services
        val response: RideResponse? = null
        return ResponseEntity.ok(response)
    }

    // TODO: Get {driverId} from token
    @PostMapping("/{rideId}/finish/{driverId}")
    fun finishRide(
        @PathVariable("rideId") rideId: Long,
        @PathVariable("driverId") driverId: Long
    ): ResponseEntity<RideResponse> {

        // TODO: Impl services
        val response: RideResponse? = null
        return ResponseEntity.ok(response)
    }

    // TODO: Get {driverId} from token
    @PostMapping("/{rideId}/rate/{driverId}")
    fun ratePassenger(
        @PathVariable rideId: Long,
        @PathVariable driverId: Long,
        @RequestBody @Valid request: RatingRequest
    ): ResponseEntity<Void> {

        // TODO: Impl services

        return ResponseEntity.ok().build()
    }

    // TODO: Get {driverId} from token
    @GetMapping("/rides/{driverId}")
    fun getAllDriverRides(
        @PathVariable driverId: Long,
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int,
        @RequestParam(value = "sort", defaultValue = "id") sortField: String
    ): ResponseEntity<Page<RideResponse>> {

        // TODO: Impl services
        val ridesResponsePage: Page<RideResponse>? = null
        return ResponseEntity.ok(ridesResponsePage)
    }
}