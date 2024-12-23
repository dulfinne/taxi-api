package com.dulfinne.taxi.paymentservice.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record WalletResponse(
    Long id,
    String username,
    BigDecimal balance,
    BigDecimal debt
) {
}
