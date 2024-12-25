package com.dulfinne.taxi.promocodeservice.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record PromocodeUsageResponse(
    String id,
    String code,
    String username,
    Instant usageDate,
    Long rideId
) {
}
