package com.dulfinne.taxi.promocodeservice.dto.request;

import com.dulfinne.taxi.promocodeservice.util.ValidationKeys;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PromocodeUsageRequest(
    @NotNull(message = ValidationKeys.CODE_REQUIRED)
    String code,

    @NotNull(message = ValidationKeys.USERNAME_REQUIRED)
    String username,

    @NotNull(message = ValidationKeys.RIDE_ID_REQUIRED)
    Integer rideId
) {
}
