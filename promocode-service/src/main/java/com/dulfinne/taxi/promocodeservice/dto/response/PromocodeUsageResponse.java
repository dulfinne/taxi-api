package com.dulfinne.taxi.promocodeservice.dto.response;

import java.time.Instant;

public record PromocodeUsageResponse(
    String id,
    String code,
    String username,
    Instant usageDate,
    Integer rideId
) {
}
