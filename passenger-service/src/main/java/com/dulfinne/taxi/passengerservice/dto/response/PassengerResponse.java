package com.dulfinne.taxi.passengerservice.dto.response;

import com.dulfinne.taxi.passengerservice.model.Payment;

public record PassengerResponse(
    Long id,
    String username,
    String firstName,
    String lastName,
    String phoneNumber,
    Payment payment,
    Integer rideCount,
    Double averageRating
) {
}
