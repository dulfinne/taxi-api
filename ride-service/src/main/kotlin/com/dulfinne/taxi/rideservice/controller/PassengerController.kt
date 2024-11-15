package com.dulfinne.taxi.rideservice.controller

import com.dulfinne.taxi.rideservice.dto.request.LocationRequest
import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.CountPriceResponse
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import com.dulfinne.taxi.rideservice.service.PassengerService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rides/passenger")
class PassengerController(val service: PassengerService) {

    @PostMapping("/price")
    fun countPrice(@RequestBody @Valid request: LocationRequest): ResponseEntity<CountPriceResponse> {
        val response = service.countPrice(request)
        return ResponseEntity.ok(response)
    }

    // TODO: Get {passengerId} from token
    @PostMapping("/{passengerId}")
    fun createRide(
        @PathVariable passengerId: Long,
        @RequestBody @Valid request: LocationRequest
    ): ResponseEntity<RideResponse> {

        val response = service.createRide(passengerId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    // TODO: Get {passengerId} from token
    @PostMapping("/{passengerId}/cancel/{rideId}")
    fun cancelRide(@PathVariable rideId: Long, @PathVariable passengerId: Long): ResponseEntity<Void> {
        service.cancelRide(rideId, passengerId)
        return ResponseEntity.ok().build()
    }

    // TODO: Get {passengerId} from token
    @PostMapping("/{passengerId}/rate/{rideId}")
    fun rateDriver(
        @PathVariable rideId: Long,
        @PathVariable passengerId: Long,
        @RequestBody @Valid request: RatingRequest
    ): ResponseEntity<Void> {

        service.rateDriver(rideId, passengerId, request)
        return ResponseEntity.ok().build()
    }

    // TODO: Get {passengerId} from token
    @GetMapping("/{passengerId}/rides")
    fun getAllPassengerRides(
        @PathVariable passengerId: Long,
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int,
        @RequestParam(value = "sort", defaultValue = "id") sortField: String
    ): ResponseEntity<Page<RideResponse>> {

        val ridesResponsePage = service.getAllPassengerRides(passengerId, offset, limit, sortField)
        return ResponseEntity.ok(ridesResponsePage)
    }

    @GetMapping("/{passengerId}/rides/{rideId}")
    fun getRideById(
        @PathVariable passengerId: Long,
        @PathVariable rideId: Long
    ): ResponseEntity<RideResponse> {

        val response = service.getRideById(passengerId, rideId)
        return ResponseEntity.ok(response)
    }
}