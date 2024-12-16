package com.dulfinne.taxi.rideservice.client.config

import com.dulfinne.taxi.rideservice.client.decoder.ClientErrorDecoder
import feign.RequestInterceptor
import feign.codec.ErrorDecoder
import org.apache.http.HttpHeaders
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken

@Configuration
class ClientConfig {

    @Bean
    fun errorDecoder(): ErrorDecoder {
        return ClientErrorDecoder()
    }

    @Bean
    fun feignRequestInterceptor(): RequestInterceptor {
        return RequestInterceptor { requestTemplate ->
            val token = getToken()
            requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer $token")
        }
    }

    fun getToken(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        val token = (authentication as JwtAuthenticationToken).token.tokenValue
        return token
    }
}