package com.dulfinne.taxi.rideservice.service.impl

import com.dulfinne.taxi.avro.Rating
import com.dulfinne.taxi.rideservice.client.service.ClientService
import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.AvailableRideResponse
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import com.dulfinne.taxi.rideservice.exception.ActionNotAllowedException
import com.dulfinne.taxi.rideservice.exception.EntityNotFoundException
import com.dulfinne.taxi.rideservice.kafka.service.KafkaProducerService
import com.dulfinne.taxi.rideservice.mapper.RideMapper
import com.dulfinne.taxi.rideservice.model.Ride
import com.dulfinne.taxi.rideservice.model.RideStatus
import com.dulfinne.taxi.rideservice.repository.RideRepository
import com.dulfinne.taxi.rideservice.service.DriverService
import com.dulfinne.taxi.rideservice.util.ExceptionKeys
import com.dulfinne.taxi.rideservice.util.RideConstants
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.TimeZone

@Service
class DriverServiceImpl(
    val repository: RideRepository,
    val mapper: RideMapper,
    val kafkaService: KafkaProducerService,
    val clientService: ClientService
) : DriverService {

    @Transactional(readOnly = true)
    override fun getAvailableRides(username: String, radius: Int): List<AvailableRideResponse> {
        val point = mapper.toPoint(clientService.getDriverLocation(username))
        val list = repository.findByStatusAndRadius(RideStatus.SEARCHING.id, point, radius)
        return list.map(mapper::toAvailableRideResponse)
    }

    @Transactional
    override fun acceptRide(rideId: Long, driverUsername: String): RideResponse {
        val driver = clientService.getDriverByUsername(driverUsername)
        if (driver.car == null) {
            throw ActionNotAllowedException(ExceptionKeys.START_NOT_ALLOWED_CAR)
        }

        val ride = getRideIfExists(rideId)
        if (ride.status != RideStatus.SEARCHING.id) {
            throw ActionNotAllowedException(ExceptionKeys.ACCEPT_NOT_ALLOWED, RideStatus.fromId(ride.status))
        }

        ride.apply {
            this.driverUsername = driverUsername
            status = RideStatus.ACCEPTED.id
        }
        repository.save(ride)
        return mapper.toRideResponse(ride)
    }

    @Transactional
    override fun startRide(rideId: Long, driverUsername: String): RideResponse {
        val ride = getRideIfExists(rideId)
        validateDriver(ride, driverUsername)

        if (ride.status != RideStatus.ACCEPTED.id) {
            throw ActionNotAllowedException(ExceptionKeys.START_NOT_ALLOWED, RideStatus.fromId(ride.status))
        }

        ride.apply {
            startTime = ZonedDateTime.now(ZoneId.of(TimeZone.getDefault().id))
            status = RideStatus.IN_PROGRESS.id
        }
        repository.save(ride)
        return mapper.toRideResponse(ride)
    }

    @Transactional
    override fun finishRide(rideId: Long, driverUsername: String): RideResponse {
        val ride = getRideIfExists(rideId)
        validateDriver(ride, driverUsername)

        if (ride.status != RideStatus.IN_PROGRESS.id) {
            throw ActionNotAllowedException(ExceptionKeys.FINISH_NOT_ALLOWED, RideStatus.fromId(ride.status))
        }

        ride.apply {
            endTime = ZonedDateTime.now(ZoneId.of(TimeZone.getDefault().id))
            status = RideStatus.COMPLETED.id
        }
        repository.save(ride)
        return mapper.toRideResponse(ride)
    }

    @Transactional
    override fun ratePassenger(rideId: Long, driverUsername: String, request: RatingRequest) {
        val ride = getValidatedRideToRate(rideId, driverUsername)

        val rating = Rating.newBuilder().apply {
            username = ride.passengerUsername
            rating = request.rating
            feedback = request.feedback
        }.build()

        updateRideStatus(ride)
        repository.save(ride)

        kafkaService.sendPassengersRating(rating)
    }

    @Transactional(readOnly = true)
    override fun getAllDriverRides(
        driverUsername: String,
        offset: Int,
        limit: Int,
        sortField: String
    ): Page<RideResponse> {
        val ridesPage = repository.findAllByDriverUsername(
            driverUsername,
            PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortField))
        )
        return ridesPage.map(mapper::toRideResponse)
    }

    @Transactional(readOnly = true)
    override fun getRideById(driverUsername: String, rideId: Long): RideResponse {
        val ride = getRideIfExists(rideId)
        validateDriver(ride, driverUsername)
        return mapper.toRideResponse(ride)
    }

    private fun checkRideCanBeRated(endTime: ZonedDateTime) {
        val duration = Duration.between(endTime, ZonedDateTime.now(ZoneId.of(TimeZone.getDefault().id))).toDays()
        if (duration > RideConstants.RATE_TIME_LIMIT_DAYS) {
            throw ActionNotAllowedException(ExceptionKeys.RATE_TIME_IS_OVER)
        }
    }

    private fun validateDriver(ride: Ride, driverUsername: String) {
        if (ride.driverUsername != driverUsername) {
            throw EntityNotFoundException(ExceptionKeys.RIDE_NOT_FOUND_ID, ride.id!!)
        }
    }

    private fun getRideIfExists(rideId: Long): Ride {
        return repository.findById(rideId).orElseThrow {
            EntityNotFoundException(ExceptionKeys.RIDE_NOT_FOUND_ID, rideId)
        }
    }

    private fun getValidatedRideToRate(rideId: Long, driverUsername: String): Ride {
        val ride = getRideIfExists(rideId)
        validateDriver(ride, driverUsername)
        if (ride.status != RideStatus.COMPLETED.id
            && ride.status != RideStatus.RATED_BY_PASSENGER.id
        ) {
            throw ActionNotAllowedException(ExceptionKeys.RATE_NOT_ALLOWED, RideStatus.fromId(ride.status))
        }

        checkRideCanBeRated(ride.endTime!!)
        return ride
    }

    private fun updateRideStatus(ride: Ride) {
        ride.status = when (ride.status) {
            RideStatus.COMPLETED.id -> RideStatus.RATED_BY_DRIVER.id
            RideStatus.RATED_BY_PASSENGER.id -> RideStatus.RATED.id
            else -> ride.status
        }
    }
}