package com.dulfinne.taxi.rideservice.client.config

import com.dulfinne.taxi.rideservice.client.decoder.ClientErrorDecoder
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientConfig {
    @Bean
    fun errorDecoder(): ErrorDecoder {
        return ClientErrorDecoder()
    }
}