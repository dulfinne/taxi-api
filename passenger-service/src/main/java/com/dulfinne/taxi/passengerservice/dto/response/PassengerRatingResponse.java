package com.dulfinne.taxi.passengerservice.dto.response;

import lombok.Builder;

@Builder
public record PassengerRatingResponse(
        Long id,
        Integer rating,
        String feedback
) {
}
