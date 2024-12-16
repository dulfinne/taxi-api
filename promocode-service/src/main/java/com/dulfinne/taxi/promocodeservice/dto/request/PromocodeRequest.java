package com.dulfinne.taxi.promocodeservice.dto.request;

import com.dulfinne.taxi.promocodeservice.model.DiscountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PromocodeRequest(
    @NotNull(message = "Please enter code")
    @Size(min = 3, max = 10, message = "Code must be between 3 and 10 characters")
    String code,

    @NotNull(message = "Please enter discount")
    @Positive(message = "Discount must be positive")
    BigDecimal discount,

    @NotNull(message = "Please enter is promocode active(true) or not(false)")
    Boolean isActive,

    @NotNull(message = "Please enter the max usage number")
    @Positive(message = "Discount must be positive")
    Integer maxUsages,

    @NotNull(message = "Please enter the type")
    DiscountType type
) {
}
