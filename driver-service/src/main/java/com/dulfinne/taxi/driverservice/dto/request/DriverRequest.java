package com.dulfinne.taxi.driverservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DriverRequest(
    @NotNull(message = "Please enter first name")
    String firstName,

    @NotNull(message = "Please enter first name")
    String lastName,

    @NotNull(message = "Please enter phone number")
    @Pattern(regexp = "^375(33|44|29|25)\\d{7}$", message = "Please enter correct phone number")
    String phoneNumber,

    @NotNull(message = "Please enter experience(age)")
    @Min(value = 2, message = "Your experience can't be under {value} years")
    @Max(value = 50, message = "Your experience can't be above {value} years")
    Integer experience
) {
}
