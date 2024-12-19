package com.dulfinne.taxi.promocodeservice.dto.request;

import com.dulfinne.taxi.promocodeservice.model.DiscountType;
import com.dulfinne.taxi.promocodeservice.util.ValidationKeys;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PromocodeRequest(
    @NotNull(message = ValidationKeys.CODE_REQUIRED)
    @Size(min = 3, max = 10, message = ValidationKeys.INVALID_CODE)
    String code,

    @NotNull(message = ValidationKeys.DISCOUNT_REQUIRED)
    @Positive(message = ValidationKeys.INVALID_DISCOUNT)
    BigDecimal discount,

    @NotNull(message = ValidationKeys.IS_ACTIVE_REQUIRED)
    Boolean isActive,

    @NotNull(message = ValidationKeys.MAX_USAGE_REQUIRED)
    @Positive(message = ValidationKeys.INVALID_MAX_USAGE)
    Integer maxUsages,

    @NotNull(message = ValidationKeys.TYPE_REQUIRED)
    DiscountType type
) {
}
