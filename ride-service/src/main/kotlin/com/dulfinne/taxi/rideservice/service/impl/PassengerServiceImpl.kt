package com.dulfinne.taxi.rideservice.service.impl

import com.dulfinne.taxi.avro.Rating
import com.dulfinne.taxi.rideservice.client.dto.DriverResponse
import com.dulfinne.taxi.rideservice.client.dto.PassengerResponse
import com.dulfinne.taxi.rideservice.client.service.ClientService
import com.dulfinne.taxi.rideservice.dto.request.LocationRequest
import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.CountPriceResponse
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import com.dulfinne.taxi.rideservice.exception.ActionNotAllowedException
import com.dulfinne.taxi.rideservice.exception.EntityNotFoundException
import com.dulfinne.taxi.rideservice.kafka.service.KafkaProducerService
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
class PassengerServiceImpl(
    val repository: RideRepository,
    val mapper: RideMapper,
    val kafkaService: KafkaProducerService,
    val clientService: ClientService
) : PassengerService {

    override fun countPrice(request: LocationRequest): CountPriceResponse {
        val startPosition = mapper.toPoint(request.startPosition)
        val endPosition = mapper.toPoint(request.endPosition)
        val price = countPriceByLocation(startPosition, endPosition)

        return mapper.toCountPriceResponse(request, price)
    }

    @Transactional
    override fun createRide(passengerUsername: String, request: LocationRequest): RideResponse {
        val passenger: PassengerResponse = clientService.getPassengerByUsername(passengerUsername)

        val ride = mapper.toRide(request, passengerUsername, passenger.payment)
        ride.price = countPriceByLocation(ride.startPosition, ride.endPosition)

        repository.save(ride)
        return mapper.toRideResponse(ride)
    }

    @Transactional
    override fun cancelRide(rideId: Long, passengerUsername: String) {
        val ride = getRideIfExists(rideId)
        validatePassenger(ride, passengerUsername)

        if (ride.status != RideStatus.SEARCHING.id) {
            throw ActionNotAllowedException(ExceptionKeys.CANCEL_NOT_ALLOWED, RideStatus.fromId(ride.status))
        }

        ride.status = RideStatus.CANCELLED.id
        repository.save(ride)
    }

    @Transactional
    override fun rateDriver(rideId: Long, passengerUsername: String, request: RatingRequest) {
        val ride = getValidatedRideToRate(rideId, passengerUsername)

        val rating = Rating.newBuilder().apply {
            username = ride.driverUsername
            rating = request.rating
            feedback = request.feedback
        }.build()

        updateRideStatus(ride)
        repository.save(ride)

        kafkaService.sendDriversRating(rating)
    }

    @Transactional(readOnly = true)
    override fun getAllPassengerRides(
        passengerUsername: String,
        offset: Int,
        limit: Int,
        sortField: String
    ): Page<RideResponse> {

        val ridesPage = repository.findAllByPassengerUsername(
            passengerUsername,
            PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortField))
        )
        return ridesPage.map(mapper::toRideResponse)
    }

    @Transactional(readOnly = true)
    override fun getRideById(passengerUsername: String, rideId: Long): RideResponse {
        val ride = getRideIfExists(rideId)
        validatePassenger(ride, passengerUsername)
        return mapper.toRideResponse(ride)
    }

    @Transactional(readOnly = true)
    override fun getDriverProfile(passengerUsername: String, rideId: Long): DriverResponse {
        val ride = getRideIfExists(rideId)
        validatePassenger(ride, passengerUsername)

        when {
            ride.status == RideStatus.ACCEPTED.id -> return clientService.getDriverByUsername(ride.driverUsername!!)
            ride.startTime != null -> checkDriverCanBeViewed(ride.startTime!!)
            else -> throw ActionNotAllowedException(
                ExceptionKeys.VIEW_DRIVER_NOT_ALLOWED, RideStatus.fromId(ride.status)
            )
        }
        return clientService.getDriverByUsername(ride.driverUsername!!)
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

        val a =
            sin(diffLat / 2).pow(2.0) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(diffLon / 2).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        val distanceKm = earthRadiusKm * c
        return BigDecimal(distanceKm).setScale(6, RoundingMode.HALF_UP)
    }

    private fun checkRideCanBeRated(endTime: ZonedDateTime) {
        val duration = Duration.between(endTime, ZonedDateTime.now(ZoneId.of(TimeZone.getDefault().id))).toDays()
        if (duration > RideConstants.RATE_TIME_LIMIT_DAYS) {
            throw ActionNotAllowedException(ExceptionKeys.RATE_TIME_IS_OVER, RideConstants.RATE_TIME_LIMIT_DAYS)
        }
    }

    private fun checkDriverCanBeViewed(startTime: ZonedDateTime) {
        val duration = Duration.between(startTime, ZonedDateTime.now(ZoneId.of(TimeZone.getDefault().id))).toDays()
        if (duration > RideConstants.VIEW_PROFILE_LIMIT_DAYS) {
            throw ActionNotAllowedException(
                ExceptionKeys.VIEW_DRIVER_TIME_IS_OVER,
                RideConstants.VIEW_PROFILE_LIMIT_DAYS
            )
        }
    }

    private fun validatePassenger(ride: Ride, passengerUsername: String) {
        if (ride.passengerUsername != passengerUsername) {
            throw EntityNotFoundException(ExceptionKeys.RIDE_NOT_FOUND_ID, ride.id!!)
        }
    }

    private fun getRideIfExists(rideId: Long): Ride {
        return repository.findById(rideId).orElseThrow {
            EntityNotFoundException(ExceptionKeys.RIDE_NOT_FOUND_ID, rideId)
        }
    }

    private fun getValidatedRideToRate(rideId: Long, passengerUsername: String): Ride {
        val ride = getRideIfExists(rideId)
        validatePassenger(ride, passengerUsername)
        if (ride.status != RideStatus.COMPLETED.id
            && ride.status != RideStatus.RATED_BY_DRIVER.id
        ) {
            throw ActionNotAllowedException(ExceptionKeys.RATE_NOT_ALLOWED, RideStatus.fromId(ride.status))
        }

        checkRideCanBeRated(ride.endTime!!)
        return ride
    }

    private fun updateRideStatus(ride: Ride) {
        ride.status = when (ride.status) {
            RideStatus.COMPLETED.id -> RideStatus.RATED_BY_PASSENGER.id
            RideStatus.RATED_BY_DRIVER.id -> RideStatus.RATED.id
            else -> ride.status
        }
    }
}