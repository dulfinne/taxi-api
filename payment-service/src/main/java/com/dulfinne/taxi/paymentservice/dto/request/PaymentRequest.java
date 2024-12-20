package com.dulfinne.taxi.paymentservice.dto.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentRequest(
    String passengerUsername,
    String driverUsername,
    BigDecimal price
) {
}
