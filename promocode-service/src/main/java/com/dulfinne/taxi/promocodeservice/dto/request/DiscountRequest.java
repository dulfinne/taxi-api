package com.dulfinne.taxi.promocodeservice.dto.request;

import com.dulfinne.taxi.promocodeservice.util.ValidationKeys;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record DiscountRequest(
    @NotNull(message = ValidationKeys.USERNAME_REQUIRED)
    String username,

    @NotNull(message = ValidationKeys.CODE_REQUIRED)
    String code,

    @NotNull(message = ValidationKeys.PRICE_REQUIRED)
    BigDecimal price
) {
}
