package com.dulfinne.taxi.driverservice.dto.response;

public record DriverInfoResponse(
    Long id,
    Long driverId,
    String firstName,
    String lastName,
    String phoneNumber,
    String experience,
    Double sumOfRatings,
    Integer numberOfRatings
) {
}
