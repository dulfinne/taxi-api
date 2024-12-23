package com.dulfinne.taxi.paymentservice.dto.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentRequest(
    Long rideId,
    String passengerUsername,
    String driverUsername,
    BigDecimal price
) {
}
