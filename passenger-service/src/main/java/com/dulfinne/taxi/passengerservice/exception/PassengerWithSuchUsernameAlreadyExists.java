package com.dulfinne.taxi.passengerservice.exception;

public class PassengerWithSuchUsernameAlreadyExists extends RuntimeException {
    public PassengerWithSuchUsernameAlreadyExists(String message) {
        super(message);
    }
}
