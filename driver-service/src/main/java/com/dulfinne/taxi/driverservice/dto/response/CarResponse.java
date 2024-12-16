package com.dulfinne.taxi.driverservice.dto.response;

import com.dulfinne.taxi.driverservice.model.CarCategory;
import lombok.Builder;

@Builder
public record CarResponse(
    Long id,
    String name,
    String color,
    String registrationNumber,
    CarCategory carCategory
) {
}
