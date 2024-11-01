package com.dulfinne.taxi.passengerservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PassengerRequest(

        @NotNull(message = "Username can't be empty")
        @Pattern(regexp = "^(?=.+[a-z])[a-z\\d]{5,20}$",
                message = "Username must contain from 5 to 20 characters and at least one letter")
        String username,

        @NotNull(message = "Password can't be empty")
        @Pattern(regexp = "^(?=.+\\d)[A-Za-z\\d]{8,20}$",
                message = "Password must contain from 8 to 20 characters and at least 1 number")
        String password,

        @NotNull(message = "Email can't be empty")
        @Email(message = "Please enter correct email")
        String email
) {
}