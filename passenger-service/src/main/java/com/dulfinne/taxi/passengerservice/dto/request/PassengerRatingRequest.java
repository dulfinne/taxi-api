package com.dulfinne.taxi.passengerservice.dto.request;

import com.dulfinne.taxi.passengerservice.util.ValidationKeys;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record PassengerRatingRequest(
    @NotNull(message = ValidationKeys.RATING_REQUIRED)
    Integer rating,

    @Length(max = 255, message = ValidationKeys.INVALID_FEEDBACK_LENGTH)
    String feedback
) {
}
