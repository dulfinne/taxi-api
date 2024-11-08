package com.dulfinne.taxi.rideservice.model

import org.locationtech.jts.geom.Point
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.ZonedDateTime

@Entity
@Table(name = "rides")
data class Ride(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "driver_id")
    var driverId: Long?,

    @Column(name = "passenger_id")
    var passengerId: Long,

    @Column(name = "price")
    var price: BigDecimal,

    @Column(name = "start_time")
    var startTime: ZonedDateTime?,

    @Column(name = "end_time")
    var endTime: ZonedDateTime?,

    @Column(name = "start_position", columnDefinition = "geography(Point, 4326)")
    var startPosition: Point,

    @Column(name = "end_position", columnDefinition = "geography(Point, 4326)")
    var endPosition: Point,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: RideStatus
)
