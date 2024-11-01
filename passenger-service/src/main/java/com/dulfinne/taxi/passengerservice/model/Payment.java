package com.dulfinne.taxi.passengerservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Payment {
    CASH("Cash Payment"),
    CARD("Card Payment");

    private final String description;
}
