package com.dulfinne.taxi.driverservice.dto.response;

import lombok.Builder;

@Builder
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
