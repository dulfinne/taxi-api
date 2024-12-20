package com.dulfinne.taxi.paymentservice.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record MoneyRequest(
        @DecimalMin(value = "2.60")
        @DecimalMax(value = "500")
        BigDecimal amount
) {
}
