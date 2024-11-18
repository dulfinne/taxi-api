package com.dulfinne.taxi.driverservice.dto.response;

public record DriverResponse(
    Long id,
    String firstName,
    String lastName,
    String phoneNumber,
    String experience,
    Double averageRating
) {
}
