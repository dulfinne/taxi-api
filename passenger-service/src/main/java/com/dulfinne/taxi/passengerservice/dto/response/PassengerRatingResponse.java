package com.dulfinne.taxi.passengerservice.dto.response;

public record PassengerRatingResponse(

        Long id,

        Long passenger_id,
        
        Long rating,

        String feedback
) {
}
