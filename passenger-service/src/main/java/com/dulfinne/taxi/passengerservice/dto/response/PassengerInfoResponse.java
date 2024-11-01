package com.dulfinne.taxi.passengerservice.dto.response;

import com.dulfinne.taxi.passengerservice.model.Payment;

public record PassengerInfoResponse(

        Long id,

        String firstName,

        String lastName,

        String phoneNumber,

        Payment payment,

        Integer rideCount,

        Double averageRating
) {
}