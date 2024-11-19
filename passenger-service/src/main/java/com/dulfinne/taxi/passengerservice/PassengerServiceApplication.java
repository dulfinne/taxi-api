package com.dulfinne.taxi.passengerservice;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.TimeZone;

@SpringBootApplication
@EnableDiscoveryClient
public class PassengerServiceApplication {

    @Value("${SYSTEM_TIMEZONE}")
    private String applicationTimeZone;

    public static void main(String[] args) {
        SpringApplication.run(PassengerServiceApplication.class, args);
    }

    @PostConstruct
    public void setApplicationTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(applicationTimeZone));
    }
}
