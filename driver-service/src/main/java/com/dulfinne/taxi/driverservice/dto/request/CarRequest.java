package com.dulfinne.taxi.driverservice.dto.request;

import com.dulfinne.taxi.driverservice.model.CarClass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CarRequest(
    @NotNull(message = "Please enter car name")
    String name,

    @NotNull(message = "Please enter color")
    String color,

    @NotNull(message = "Please enter registration number")
    @Pattern(
            regexp = "\\d{4}[A-Z]{2}-[1-7]",
            message = "Please enter correct registration number, according to NNNNLL-N")
    String registrationNumber,

    @NotNull(message = "Please enter car class: (ECONOM/BUISENESS)")
    CarClass carClass
) {
}
