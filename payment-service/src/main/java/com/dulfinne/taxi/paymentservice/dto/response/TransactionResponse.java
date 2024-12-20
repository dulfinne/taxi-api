package com.dulfinne.taxi.paymentservice.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
public record TransactionResponse(
    Long id,
    String username,
    BigDecimal amount,
    ZonedDateTime transactionTime
) {
}
