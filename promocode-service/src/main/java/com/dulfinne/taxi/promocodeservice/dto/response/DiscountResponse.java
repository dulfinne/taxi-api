package com.dulfinne.taxi.promocodeservice.dto.response;

import java.math.BigDecimal;

public record DiscountResponse (
        BigDecimal discount
) {
}
