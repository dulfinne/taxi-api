package com.dulfinne.taxi.paymentservice.dto.request;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record MoneyRequest(
        @DecimalMin(value = "0")
        BigDecimal amount
) {
}
