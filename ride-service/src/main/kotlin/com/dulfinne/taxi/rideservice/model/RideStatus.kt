package com.dulfinne.taxi.rideservice.model

enum class RideStatus(val description: String) {
    SEARCHING("searching for driver"),
    ACCEPTED("accepted by driver"),
    IN_PROGRESS("ride is in progress"),
    COMPLETED("ride is completed"),
    CANCELLED("ride is canceled"),
}