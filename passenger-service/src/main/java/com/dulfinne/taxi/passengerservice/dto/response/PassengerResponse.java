package com.dulfinne.taxi.passengerservice.dto.response;

import java.time.ZonedDateTime;

public record PassengerResponse(

        Long id,

        String username,

        String email,

        ZonedDateTime createdAt
) {
}