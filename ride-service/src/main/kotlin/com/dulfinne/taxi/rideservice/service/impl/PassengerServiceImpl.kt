package com.dulfinne.taxi.rideservice.service.impl

import com.dulfinne.taxi.rideservice.dto.request.LocationRequest
import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.CountPriceResponse
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import com.dulfinne.taxi.rideservice.exception.ActionNotAllowedException
import com.dulfinne.taxi.rideservice.exception.EntityNotFoundException
import com.dulfinne.taxi.rideservice.mapper.RideMapper
import com.dulfinne.taxi.rideservice.model.Ride
import com.dulfinne.taxi.rideservice.model.RideStatus
import com.dulfinne.taxi.rideservice.repository.RideRepository
import com.dulfinne.taxi.rideservice.service.PassengerService
import com.dulfinne.taxi.rideservice.util.ExceptionKeys
import com.dulfinne.taxi.rideservice.util.RideConstants
import org.locationtech.jts.geom.Point
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.TimeZone
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

@Service
class PassengerServiceImpl(val repository: RideRepository, val mapper: RideMapper) : PassengerService {

    override fun countPrice(request: LocationRequest): CountPriceResponse {
        val startPosition = mapper.toPoint(request.startPosition)
        val endPosition = mapper.toPoint(request.endPosition)
        val price = countPriceByLocation(startPosition, endPosition)

        return mapper.toCountPriceResponse(request, price)
    }

    @Transactional
    override fun createRide(passengerId: Long, request: LocationRequest): RideResponse {
        val ride = mapper.toRide(request, passengerId)
        ride.price = countPriceByLocation(ride.startPosition, ride.endPosition)

        repository.save(ride)
        return mapper.toRideResponse(ride)
    }

    @Transactional
    override fun cancelRide(rideId: Long, passengerId: Long) {
        val ride = getRideIfExists(rideId)
        validatePassenger(ride, passengerId)

        if (ride.status != RideStatus.SEARCHING.id) {
            throw ActionNotAllowedException(ExceptionKeys.CANCEL_NOT_ALLOWED, RideStatus.fromId(ride.status))
        }

        ride.status = RideStatus.CANCELLED.id
        repository.save(ride)
    }

    @Transactional(readOnly = true)
    override fun rateDriver(rideId: Long, passengerId: Long, request: RatingRequest) {
        val ride = getRideIfExists(rideId)
        validatePassenger(ride, passengerId)

        if (ride.status != RideStatus.COMPLETED.id) {
            throw ActionNotAllowedException(ExceptionKeys.RATE_NOT_ALLOWED, RideStatus.fromId(ride.status))
        }
        checkRideCanBeRated(ride.endTime!!)

        //TODO: send ratingModel to driver-service
    }

    @Transactional(readOnly = true)
    override fun getAllPassengerRides(
        passengerId: Long,
        offset: Int,
        limit: Int,
        sortField: String
    ): Page<RideResponse> {

        val ridesPage = repository.findAllByPassengerId(
            passengerId,
            PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortField))
        )
        return ridesPage.map(mapper::toRideResponse)
    }

    @Transactional(readOnly = true)
    override fun getRideById(passengerId: Long, rideId: Long): RideResponse {
        val ride = getRideIfExists(rideId)
        validatePassenger(ride, passengerId)
        return mapper.toRideResponse(ride)
    }

    private fun countPriceByLocation(startPosition: Point, endPosition: Point): BigDecimal {
        val basePrice = RideConstants.BASE_PRICE
        val pricePerKm = RideConstants.PRICE_PER_KM

        val distanceKm = haversineDistance(startPosition.x, startPosition.y, endPosition.x, endPosition.y)
        val totalPrice = basePrice.add(distanceKm.multiply(pricePerKm))

        return totalPrice.setScale(2, RoundingMode.HALF_UP)
    }

    fun haversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): BigDecimal {
        val earthRadiusKm = 6371.0
        val diffLat = Math.toRadians(lat2 - lat1)
        val diffLon = Math.toRadians(lon2 - lon1)

        val a = sin(diffLat / 2).pow(2.0) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(diffLon / 2).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        val distanceKm = earthRadiusKm * c
        return BigDecimal(distanceKm).setScale(6, RoundingMode.HALF_UP)
    }

    private fun checkRideCanBeRated(endTime: ZonedDateTime) {
        val duration = Duration.between(endTime, ZonedDateTime.now(ZoneId.of(TimeZone.getDefault().id))).toDays()
        if (duration > RideConstants.RATE_TIME_LIMIT_DAYS) {
            throw ActionNotAllowedException(ExceptionKeys.RATE_TIME_IS_OVER)
        }
    }

    private fun validatePassenger(ride: Ride, passengerId: Long) {
        if (ride.passengerId != passengerId) {
            throw EntityNotFoundException(ExceptionKeys.RIDE_NOT_FOUND_ID, ride.id!!)
        }
    }

    private fun getRideIfExists(rideId: Long): Ride {
        return repository.findById(rideId).orElseThrow {
            EntityNotFoundException(ExceptionKeys.RIDE_NOT_FOUND_ID, rideId)
        }
    }
}