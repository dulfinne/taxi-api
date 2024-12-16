package com.dulfinne.taxi.rideservice.mapper

import com.dulfinne.taxi.rideservice.dto.request.LocationRequest
import com.dulfinne.taxi.rideservice.dto.request.PointRequest
import com.dulfinne.taxi.rideservice.dto.response.CountPriceResponse
import com.dulfinne.taxi.rideservice.dto.response.PointResponse
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import com.dulfinne.taxi.rideservice.model.Payment
import com.dulfinne.taxi.rideservice.model.Ride
import com.dulfinne.taxi.rideservice.model.RideStatus
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.PrecisionModel
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class RideMapper {
    companion object {
        private val geometryFactory = GeometryFactory(PrecisionModel(), 4326)
    }

    fun toCountPriceResponse(request: LocationRequest, price: BigDecimal): CountPriceResponse {
        return CountPriceResponse(
            startPosition = toPointResponse(request.startPosition),
            endPosition = toPointResponse(request.endPosition),
            price = price
        )
    }

    fun toRide(request: LocationRequest, passengerUsername: String, payment: Payment): Ride {
        return Ride(
            id = null,
            driverUsername = null,
            passengerUsername = passengerUsername,
            price = BigDecimal.ZERO,
            payment = payment.id,
            startPosition = toPoint(request.startPosition),
            endPosition = toPoint(request.endPosition),
            startTime = null,
            endTime = null,
            status = RideStatus.SEARCHING.id
        )
    }

    fun toRideResponse(entity: Ride): RideResponse {
        return RideResponse(
            id = entity.id!!,
            driverUsername = entity.driverUsername,
            passengerUsername = entity.passengerUsername,
            price = entity.price,
            payment = Payment.fromId(entity.payment),
            startPosition = toPointResponse(entity.startPosition),
            endPosition = toPointResponse(entity.endPosition),
            startTime = entity.startTime,
            endTime = entity.endTime,
            status = RideStatus.fromId(entity.status)
        )
    }

    fun toPoint(requestPoint: PointRequest): Point {
        val coordinate = Coordinate(requestPoint.longitude.toDouble(), requestPoint.latitude.toDouble())
        return geometryFactory.createPoint(coordinate)
    }

    private fun toPointResponse(request: PointRequest): PointResponse {
        return PointResponse(
            latitude = request.latitude,
            longitude = request.longitude
        )
    }

    private fun toPointResponse(point: Point): PointResponse {
        return PointResponse(
            latitude = BigDecimal.valueOf(point.y),
            longitude = BigDecimal.valueOf(point.x)
        )
    }
}