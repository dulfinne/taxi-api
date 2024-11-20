package com.dulfinne.taxi.passengerservice.dto.request;

import com.dulfinne.taxi.passengerservice.model.Payment;
import com.dulfinne.taxi.passengerservice.util.RegexPatterns;
import com.dulfinne.taxi.passengerservice.util.ValidationKeys;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PassengerRequest(
    @NotNull(message = ValidationKeys.FIRST_NAME_REQUIRED)
    String firstName,

    @NotNull(message = ValidationKeys.LAST_NAME_REQUIRED)
    String lastName,

    @NotNull(message = ValidationKeys.PHONE_NUMBER_REQUIRED)
    @Pattern(regexp = RegexPatterns.PHONE_NUMBER_FORMAT, message = ValidationKeys.INVALID_PHONE_NUMBER)
    String phoneNumber,

    @NotNull(message = ValidationKeys.PAYMENT_REQUIRED)
    Payment payment
) {
}
