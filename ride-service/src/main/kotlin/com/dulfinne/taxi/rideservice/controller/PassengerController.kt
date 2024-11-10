package com.dulfinne.taxi.rideservice.controller

import com.dulfinne.taxi.rideservice.dto.request.LocationRequest
import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.CountPriceResponse
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rides/passenger")
class PassengerController {

    @PostMapping("/count-price")
    fun countPrice(@RequestBody @Valid request: LocationRequest): ResponseEntity<CountPriceResponse> {

        // TODO: Impl services

        val response: CountPriceResponse? = null
        return ResponseEntity.ok(response)
    }

    // TODO: Get {passengerId} from token
    @PostMapping("/create-ride/{passengerId}")
    fun createRide(
        @PathVariable passengerId: Long,
        @RequestBody @Valid request: LocationRequest
    ): ResponseEntity<RideResponse> {

        // TODO: Impl services

        val response: RideResponse? = null
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    // TODO: Get {passengerId} from token
    @DeleteMapping("/{rideId}/cancel-ride/{passengerId}")
    fun cancelRide(@PathVariable rideId: Long, @PathVariable passengerId: Long): ResponseEntity<Void> {

        // TODO: Impl services

        return ResponseEntity.noContent().build()
    }

    // TODO: Get {passengerId} from token
    @PostMapping("/{rideId}/rate/{passengerId}")
    fun rateDriver(
        @PathVariable rideId: Long,
        @PathVariable passengerId: Long,
        @RequestBody @Valid request: RatingRequest
    ): ResponseEntity<Void> {

        // TODO: Impl services

        return ResponseEntity.ok().build()
    }

    // TODO: Get {passengerId} from token
    @GetMapping("/rides/{passengerId}")
    fun getAllPassengerRides(
        @PathVariable passengerId: Long,
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int,
        @RequestParam(value = "sort", defaultValue = "id") sortField: String
    ): ResponseEntity<Page<RideResponse>> {

        // TODO: Impl services
        val ridesResponsePage: Page<RideResponse>? = null
        return ResponseEntity.ok(ridesResponsePage)
    }
}