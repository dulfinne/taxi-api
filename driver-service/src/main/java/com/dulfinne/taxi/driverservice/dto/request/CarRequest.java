package com.dulfinne.taxi.driverservice.dto.request;

import com.dulfinne.taxi.driverservice.model.CarCategory;
import com.dulfinne.taxi.driverservice.util.ValidationKeys;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CarRequest(
    @NotNull(message = ValidationKeys.CAR_NAME_REQUIRED)
    String name,

    @NotNull(message = ValidationKeys.COLOR_REQUIRED)
    String color,

    @NotNull(message = ValidationKeys.REGISTRATION_NUMBER_REQUIRED)
    @Pattern(
            regexp = ValidationKeys.REGISTRATION_NUMBER_FORMAT,
            message = ValidationKeys.INVALID_REGISTRATION_NUMBER)
    String registrationNumber,

    @NotNull(message = ValidationKeys.CAR_CATEGORY_REQUIRED)
    CarCategory carCategory
) {
}
