package com.dulfinne.taxi.rideservice.controller.api

import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import com.dulfinne.taxi.rideservice.exception.ErrorResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Ride Controller", description = "Admin managing rides")
interface RideApi {

    @Operation(
        operationId = "getAllRides",
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
    @GetMapping
    fun getAllRides(
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
    @GetMapping("/{rideId}")
    fun getRideById(@PathVariable rideId: Long): ResponseEntity<RideResponse>

    @Operation(
        operationId = "deleteRideById",
        summary = "Delete ride by id"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",

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
    @DeleteMapping("/{rideId}")
    fun deleteRideById(@PathVariable rideId: Long): ResponseEntity<Void>
}