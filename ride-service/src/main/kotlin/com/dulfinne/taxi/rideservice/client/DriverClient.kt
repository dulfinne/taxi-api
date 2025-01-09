package com.dulfinne.taxi.rideservice.client

import com.dulfinne.taxi.rideservice.client.config.ClientConfig
import com.dulfinne.taxi.rideservice.client.dto.DriverResponse
import com.dulfinne.taxi.rideservice.dto.response.PointResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "\${client.driver-service.name}",
    path = "\${client.driver-service.path}",
    configuration = [ClientConfig::class]
)
interface DriverClient {
    @GetMapping("/{username}")
    fun getDriverByUsername(@PathVariable username: String): DriverResponse

    @GetMapping("/{username}/location")
    fun getDriverLocationByUsername(@PathVariable username: String): PointResponse
}