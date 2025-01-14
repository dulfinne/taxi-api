package com.dulfinne.taxi.rideservice.controller.api

import com.dulfinne.taxi.rideservice.client.dto.DriverResponse
import com.dulfinne.taxi.rideservice.dto.request.LocationRequest
import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.CountPriceResponse
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

@Tag(name = "Passenger Controller", description = "Passenger managing ride")
interface PassengerApi {
    @Operation(
        operationId = "countPrice",
        summary = "Counts predicted cost of ride"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CountPriceResponse::class)
                )
            ]
        )
    )
    @PostMapping("/price")
    fun countPrice(
        username: String,
        @RequestBody @Valid request: LocationRequest,
        @RequestParam(defaultValue = "") promocode: String
    ): ResponseEntity<CountPriceResponse>


    @Operation(
        operationId = "createRide",
        summary = "Create ride"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = RideResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "409", description = "Not allowed to create ride",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    @PostMapping
    fun createRide(
        username: String,
        @RequestBody @Valid request: LocationRequest,
        @RequestParam(defaultValue = "") promocode: String
    ): ResponseEntity<RideResponse>

    @Operation(
        operationId = "cancelRide",
        summary = "Cancel ride"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
        ),
        ApiResponse(
            responseCode = "409", description = "Not allowed to cancel ride",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    @PostMapping("/cancel/{rideId}")
    fun cancelRide(
        username: String,
        @PathVariable rideId: Long
    ): ResponseEntity<Void>

    @Operation(
        operationId = "rateDriver",
        summary = "Rate driver"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
        ),
        ApiResponse(
            responseCode = "409", description = "Not allowed to rate driver",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    @PostMapping("/rate/{rideId}")
    fun rateDriver(
        username: String,
        @PathVariable rideId: Long,
        @RequestBody @Valid request: RatingRequest
    ): ResponseEntity<Void>

    @Operation(
        operationId = "getAllPassengerRides",
        summary = "Get all passenger rides"
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
    fun getAllPassengerRides(
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

    @Operation(
        operationId = "getDriverProfile",
        summary = "Get driver profile"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = DriverResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "409", description = "Not allowed to view driver profile",
            content = [
                Content(
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    @GetMapping("/rides/{rideId}/driver-profile")
    fun getDriverProfile(
        username: String,
        @PathVariable rideId: Long
    ): ResponseEntity<DriverResponse>
}