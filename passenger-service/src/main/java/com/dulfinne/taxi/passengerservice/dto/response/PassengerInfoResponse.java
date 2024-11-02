package com.dulfinne.taxi.passengerservice.dto.response;

import com.dulfinne.taxi.passengerservice.model.Payment;

public record PassengerInfoResponse(
    Long id,
    Long passengerId,
    String firstName,
    String lastName,
    String phoneNumber,
    Payment payment,
    Integer rideCount,
    Double averageRating
) {}
