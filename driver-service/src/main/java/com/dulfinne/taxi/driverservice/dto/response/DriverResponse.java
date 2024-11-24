package com.dulfinne.taxi.driverservice.dto.response;

import com.dulfinne.taxi.driverservice.model.Car;

public record DriverResponse(
    Long id,
    String username,
    String firstName,
    String lastName,
    String phoneNumber,
    String experience,
    Car car,
    Double averageRating
) {
}
