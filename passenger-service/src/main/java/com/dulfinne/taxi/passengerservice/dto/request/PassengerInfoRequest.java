package com.dulfinne.taxi.passengerservice.dto.request;

import com.dulfinne.taxi.passengerservice.model.Payment;
import jakarta.validation.constraints.Pattern;

public record PassengerInfoRequest(
    String firstName,
    String lastName,
    @Pattern(regexp = "^375(33|44|29|25)\\d{7}$", message = "Please enter correct phone number")
        String phoneNumber,
    Payment payment) {}
