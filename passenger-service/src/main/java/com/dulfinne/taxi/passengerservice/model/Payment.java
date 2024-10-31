package com.dulfinne.taxi.passengerservice.model;

public enum Payment {
    CASH("Cash Payment"),
    CARD("Card Payment");

    public final String description;

    private Payment(String description) {
        this.description = description;
    }
}
