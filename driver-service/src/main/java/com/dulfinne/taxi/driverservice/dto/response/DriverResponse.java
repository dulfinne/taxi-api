package com.dulfinne.taxi.driverservice.dto.response;

public record DriverResponse(
    Long id,
    String username,
    String firstName,
    String lastName,
    String phoneNumber,
    String experience,
    CarResponse car,
    Double averageRating
) {
}
