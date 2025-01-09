package com.dulfinne.taxi.rideservice.client

import com.dulfinne.taxi.rideservice.client.config.ClientConfig
import com.dulfinne.taxi.rideservice.client.dto.request.DiscountRequest
import com.dulfinne.taxi.rideservice.client.dto.DiscountResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "\${client.promocode-service.name}",
    path = "\${client.promocode-service.path}",
    configuration = [ClientConfig::class]
)
interface PromocodeClient {
    @PostMapping("/discount")
    fun getDiscount(@RequestBody discount: DiscountRequest): DiscountResponse
}