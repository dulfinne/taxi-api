package com.dulfinne.taxi.rideservice.repository

import com.dulfinne.taxi.rideservice.model.Ride
import org.springframework.data.jpa.repository.JpaRepository

interface RideRepository : JpaRepository<Ride, Long> {
}