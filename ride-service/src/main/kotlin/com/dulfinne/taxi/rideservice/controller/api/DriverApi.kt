package com.dulfinne.taxi.rideservice.controller.api

import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.AvailableRideResponse
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import com.dulfinne.taxi.rideservice.exception.ErrorResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Driver Controller", description = "Driver managing ride")
interface DriverApi {

    @Operation(
        operationId = "getAvailableRides",
        summary = "Get available rides in radius"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = AvailableRideResponse::class)
                )
            ]
        )
    )
    @GetMapping("/available-rides")
    fun getAvailableRides(
        username: String,
        @RequestParam(value = "radius", defaultValue = "700") radius: Int,
    ): ResponseEntity<List<AvailableRideResponse>>


    @Operation(
        operationId = "acceptRide",
        summary = "Accept ride"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = RideResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "409", description = "Not allowed to accept ride",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    @PostMapping("/accept/{rideId}")
    fun acceptRide(
        username: String,
        @PathVariable("rideId") rideId: Long
    ): ResponseEntity<RideResponse>

    @Operation(
        operationId = "startRide",
        summary = "Start ride"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = RideResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "409", description = "Not allowed to start ride",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    @PostMapping("/start/{rideId}")
    fun startRide(
        username: String,
        @PathVariable("rideId") rideId: Long
    ): ResponseEntity<RideResponse>

    @Operation(
        operationId = "finishRide",
        summary = "Finish ride"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = RideResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "409", description = "Not allowed to finish ride",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    @PostMapping("/finish/{rideId}")
    fun finishRide(
        username: String,
        @PathVariable("rideId") rideId: Long
    ): ResponseEntity<RideResponse>

    @Operation(
        operationId = "ratePassenger",
        summary = "Rate passenger"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
        ),
        ApiResponse(
            responseCode = "409", description = "Not allowed to rate",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    @PostMapping("/rate/{rideId}")
    fun ratePassenger(
        username: String,
        @PathVariable rideId: Long,
        @RequestBody @Valid request: RatingRequest
    ): ResponseEntity<Void>

    @Operation(
        operationId = "getAllDriverRides",
        summary = "Get all rides"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = RideResponse::class)
                )
            ]
        )
    )
    @GetMapping("/rides")
    fun getAllDriverRides(
        username: String,
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int,
        @RequestParam(value = "sort", defaultValue = "id") sortField: String
    ): ResponseEntity<Page<RideResponse>>

    @Operation(
        operationId = "getRideById",
        summary = "Get ride by id"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = RideResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404", description = "Ride not found",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    @GetMapping("/rides/{rideId}")
    fun getRideById(
        username: String,
        @PathVariable rideId: Long
    ): ResponseEntity<RideResponse>
}