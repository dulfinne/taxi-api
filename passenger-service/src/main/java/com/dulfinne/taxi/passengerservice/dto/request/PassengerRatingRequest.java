package com.dulfinne.taxi.passengerservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record PassengerRatingRequest(

        @NotNull(message = "Rating can't be empty")
        Long rating,

        @Length(max = 255, message = "Feedback must be under {max} characters")
        String feedback
) {
}
