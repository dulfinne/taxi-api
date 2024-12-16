package com.dulfinne.taxi.promocodeservice.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DiscountRequest(
    @NotNull(message = "Please enter username")
    String username,

    @NotNull(message = "Please enter code")
    String code,

    @NotNull(message = "Please enter price")
    BigDecimal price
) {
}
