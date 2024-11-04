package com.dulfinne.taxi.driverservice.dto.request;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record DriverRatingRequest(
    @NotNull(message = "Rating can't be empty")
    Integer rating,

    @Length(max = 255, message = "Feedback must be under {max} characters")
    String feedback
) {
}
