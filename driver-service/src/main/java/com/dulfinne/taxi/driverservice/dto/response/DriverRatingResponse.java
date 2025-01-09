package com.dulfinne.taxi.driverservice.dto.response;

import lombok.Builder;

@Builder
public record DriverRatingResponse(
        Long id,
        Integer rating,
        String feedback
) {
}
