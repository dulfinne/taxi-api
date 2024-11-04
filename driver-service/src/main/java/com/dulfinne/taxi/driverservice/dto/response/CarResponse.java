package com.dulfinne.taxi.driverservice.dto.response;

import com.dulfinne.taxi.driverservice.model.CarClass;

public record CarResponse(
    Long id,
    String name,
    String color,
    String registrationNumber,
    CarClass carClass
) {
}
