package com.dulfinne.taxi.rideservice.client

import com.dulfinne.taxi.rideservice.client.config.ClientConfig
import com.dulfinne.taxi.rideservice.client.dto.PassengerResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "\${client.passenger-service.name}",
    path = "\${client.passenger-service.path}",
    configuration = [ClientConfig::class]
)
interface PassengerClient {
    @GetMapping("/{username}")
    fun getPassengerByUsername(@PathVariable username: String): PassengerResponse
}