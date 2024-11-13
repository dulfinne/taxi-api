package com.dulfinne.taxi.rideservice

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.TimeZone

@SpringBootApplication
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
