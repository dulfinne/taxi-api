package com.dulfinne.taxi.paymentservice.dto.request;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MoneyRequest(
        @Positive
        BigDecimal amount
) {
}
