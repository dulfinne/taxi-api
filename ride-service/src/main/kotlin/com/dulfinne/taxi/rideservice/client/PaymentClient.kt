package com.dulfinne.taxi.rideservice.client

import com.dulfinne.taxi.rideservice.client.config.ClientConfig
import com.dulfinne.taxi.rideservice.client.dto.CanPayByCardResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(
    name = "\${client.payment-service.name}",
    path = "\${client.payment-service.path}",
    configuration = [ClientConfig::class]
)
interface PaymentClient {
    @PostMapping("/wallets/{username}/card-payment-check")
    fun canPayWithCard(@PathVariable username: String?): CanPayByCardResponse
}