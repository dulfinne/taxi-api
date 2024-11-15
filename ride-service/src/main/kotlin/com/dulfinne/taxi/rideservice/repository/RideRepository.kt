package com.dulfinne.taxi.rideservice.repository

import com.dulfinne.taxi.rideservice.model.Ride
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface RideRepository : JpaRepository<Ride, Long> {
    fun findAllByPassengerId(passengerId: Long, pageable: Pageable): Page<Ride>
    fun findAllByDriverId(driverId: Long, pageable: Pageable): Page<Ride>
    fun findAllByStatus(statusId: Int, pageable: Pageable): Page<Ride>
}