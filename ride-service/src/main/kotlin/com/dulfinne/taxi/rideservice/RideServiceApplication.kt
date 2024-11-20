package com.dulfinne.taxi.rideservice

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import java.util.TimeZone

@SpringBootApplication
@EnableDiscoveryClient
class RideServiceApplication {

    @Value("\${SYSTEM_TIMEZONE}")
    private lateinit var applicationTimeZone: String

    @PostConstruct
    fun setApplicationTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(applicationTimeZone))
    }
}

fun main(args: Array<String>) {
    runApplication<RideServiceApplication>(*args)
}
