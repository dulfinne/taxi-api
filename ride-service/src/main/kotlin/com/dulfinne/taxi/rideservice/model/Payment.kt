package com.dulfinne.taxi.rideservice.model

enum class Payment(val id: Int) {
    CASH(1),
    CARD(2);

    companion object {
        fun fromId(id: Int): Payment {
            return entries.find { it.id == id }
                ?: throw IllegalArgumentException("Invalid id for Payment: $id")
        }
    }
}