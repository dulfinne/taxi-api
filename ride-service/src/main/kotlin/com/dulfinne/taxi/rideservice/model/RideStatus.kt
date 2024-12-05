package com.dulfinne.taxi.rideservice.model

enum class RideStatus(val id: Int) {
    SEARCHING(1),
    ACCEPTED(2),
    IN_PROGRESS(3),
    COMPLETED(4),
    CANCELLED(5),
    RATED_BY_PASSENGER(6),
    RATED_BY_DRIVER(7),
    RATED(8);

    companion object {
        fun fromId(id: Int): RideStatus {
            return entries.find { it.id == id }
                ?: throw IllegalArgumentException("Invalid id for RideStatus: $id")
        }
    }
}