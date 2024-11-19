package com.dulfinne.taxi.rideservice.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.locationtech.jts.geom.Point
import java.math.BigDecimal
import java.time.ZonedDateTime

@Entity
@Table(
    name = "rides",
    indexes = [
        Index(name = "idx_passenger_username", columnList = "passenger_username"),
        Index(name = "idx_driver_username", columnList = "driver_username")
    ]
)
data class Ride(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long?,

    @Column(name = "driver_username")
    var driverUsername: String?,

    @Column(name = "passenger_username")
    val passengerUsername: String,

    @Column(name = "price")
    var price: BigDecimal,

    @Column(name = "start_time")
    var startTime: ZonedDateTime?,

    @Column(name = "end_time")
    var endTime: ZonedDateTime?,

    @Column(name = "start_position", columnDefinition = "geography(Point, 4326)")
    val startPosition: Point,

    @Column(name = "end_position", columnDefinition = "geography(Point, 4326)")
    val endPosition: Point,

    @Column(name = "status")
    var status: Int
)
