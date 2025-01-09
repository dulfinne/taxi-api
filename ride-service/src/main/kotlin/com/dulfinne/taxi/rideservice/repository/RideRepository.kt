package com.dulfinne.taxi.rideservice.repository

import com.dulfinne.taxi.rideservice.model.Ride
import org.locationtech.jts.geom.Point
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface RideRepository : JpaRepository<Ride, Long> {
    fun findAllByPassengerUsername(passengerUsername: String, pageable: Pageable): Page<Ride>
    fun findAllByDriverUsername(driverUsername: String, pageable: Pageable): Page<Ride>
    fun findAllByStatus(statusId: Int, pageable: Pageable): Page<Ride>

    @Query(value = """
        SELECT *
        FROM
            rides
        WHERE
            status = :status_id
            AND ST_DWithin(geography(start_position), :point, :radius)
        ORDER BY id ASC
        LIMIT 5
        """,
        nativeQuery = true)
    fun findByStatusAndRadius(
        @Param("status_id") statusId: Int,
        @Param("point") point: Point,
        @Param("radius") radius: Int,
    ): List<Ride>
}