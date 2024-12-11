package com.dulfinne.taxi.promocodeservice.dto.request;

import jakarta.validation.constraints.NotNull;

public record PromocodeUsageRequest(
    @NotNull(message = "Please enter code")
    String code,

    @NotNull(message = "Please enter username")
    String username,

    @NotNull(message = "Please enter ride id")
    Integer rideId
) {
}
