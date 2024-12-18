package com.dulfinne.taxi.promocodeservice.dto.response;

import com.dulfinne.taxi.promocodeservice.model.DiscountType;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PromocodeResponse(
    String id,
    String code,
    BigDecimal discount,
    Boolean isActive,
    Integer maxUsages,
    Integer usageCount,
    DiscountType type
) {
}
