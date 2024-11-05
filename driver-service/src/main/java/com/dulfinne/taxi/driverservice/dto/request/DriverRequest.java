package com.dulfinne.taxi.driverservice.dto.request;

import com.dulfinne.taxi.driverservice.util.ValidationKeys;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DriverRequest(
    @NotNull(message = ValidationKeys.FIRST_NAME_REQUIRED)
    String firstName,

    @NotNull(message = ValidationKeys.LAST_NAME_REQUIRED)
    String lastName,

    @NotNull(message = ValidationKeys.PHONE_NUMBER_REQUIRED)
    @Pattern(regexp = ValidationKeys.PHONE_NUMBER_FORMAT, message = ValidationKeys.INVALID_PHONE_NUMBER)
    String phoneNumber,

    @NotNull(message = ValidationKeys.EXPERIENCE_REQUIRED)
    @Min(value = 2, message = ValidationKeys.INVALID_MIN_EXPERIENCE)
    @Max(value = 50, message = ValidationKeys.INVALID_MAX_EXPERIENCE)
    Integer experience
) {
}
