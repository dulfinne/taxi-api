package com.dulfinne.taxi.driverservice.dto.response;

public record DriverRatingResponse(
        Long id,
        Integer rating,
        String feedback
) {
}
