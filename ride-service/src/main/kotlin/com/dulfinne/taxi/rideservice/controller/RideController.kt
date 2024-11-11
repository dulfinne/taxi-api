package com.dulfinne.taxi.rideservice.controller

import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rides")
class RideController {

    @GetMapping
    fun getAvailableRides(
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "limit", defaultValue = "10") limit: Int,
        @RequestParam(value = "sort", defaultValue = "id") sortField: String
    ): ResponseEntity<Page<RideResponse>> {

        // TODO: Impl services
        val ridesResponsePage: Page<RideResponse>? = null
        return ResponseEntity.ok(ridesResponsePage)
    }

    @GetMapping("/{rideId}")
    fun getRideById(@PathVariable rideId: Long): ResponseEntity<RideResponse> {

        // TODO: Impl services
        val rideResponse: RideResponse? = null
        return ResponseEntity.ok(rideResponse)
    }

    @DeleteMapping("/{rideId}")
    fun deleteRideById(@PathVariable rideId: Long): ResponseEntity<Void> {

        // TODO: Impl services
        return ResponseEntity.noContent().build()
    }
}