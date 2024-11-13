package com.dulfinne.taxi.rideservice.service.impl

import com.dulfinne.taxi.rideservice.dto.request.RatingRequest
import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import com.dulfinne.taxi.rideservice.exception.ActionNotAllowedException
import com.dulfinne.taxi.rideservice.exception.EntityNotFoundException
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
class DriverServiceImpl(val repository: RideRepository, val mapper: RideMapper) : DriverService {

    @Transactional(readOnly = true)
    override fun getAvailableRides(offset: Int, limit: Int, sortField: String): Page<RideResponse> {
        val ridesPage = repository.findAllByStatus(
            RideStatus.SEARCHING.id,
            PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortField))
        )

        return ridesPage.map(mapper::toRideResponse)
    }

    @Transactional
    override fun acceptRide(rideId: Long, driverId: Long): RideResponse {
        val ride = getRideIfExists(rideId)

        if (ride.status != RideStatus.SEARCHING.id) {
            throw ActionNotAllowedException(ExceptionKeys.ACCEPT_NOT_ALLOWED, RideStatus.fromId(ride.status))
        }

        ride.driverId = driverId
        ride.status = RideStatus.ACCEPTED.id
        repository.save(ride)
        return mapper.toRideResponse(ride)
    }

    @Transactional
    override fun startRide(rideId: Long, driverId: Long): RideResponse {
        val ride = getRideIfExists(rideId)
        validateDriver(ride, driverId)

        if (ride.status != RideStatus.ACCEPTED.id) {
            throw ActionNotAllowedException(ExceptionKeys.START_NOT_ALLOWED, RideStatus.fromId(ride.status))
        }

        ride.startTime = ZonedDateTime.now(
            ZoneId.of(TimeZone.getDefault().id)
        )
        ride.status = RideStatus.IN_PROGRESS.id
        repository.save(ride)
        return mapper.toRideResponse(ride)
    }

    @Transactional
    override fun finishRide(rideId: Long, driverId: Long): RideResponse {
        val ride = getRideIfExists(rideId)
        validateDriver(ride, driverId)

        if (ride.status != RideStatus.IN_PROGRESS.id) {
            throw ActionNotAllowedException(ExceptionKeys.FINISH_NOT_ALLOWED, RideStatus.fromId(ride.status))
        }

        ride.endTime = ZonedDateTime.now(
            ZoneId.of(TimeZone.getDefault().id)
        )
        ride.status = RideStatus.COMPLETED.id
        repository.save(ride)
        return mapper.toRideResponse(ride)
    }

    @Transactional(readOnly = true)
    override fun ratePassenger(rideId: Long, driverId: Long, request: RatingRequest) {
        val ride = getRideIfExists(rideId)
        validateDriver(ride, driverId)

        if (ride.status != RideStatus.COMPLETED.id) {
            throw ActionNotAllowedException(ExceptionKeys.RATE_NOT_ALLOWED, RideStatus.fromId(ride.status))
        }
        checkRideCanBeRated(ride.endTime!!)

        //TODO: send ratingModel to passenger-service
    }

    @Transactional(readOnly = true)
    override fun getAllDriverRides(driverId: Long, offset: Int, limit: Int, sortField: String): Page<RideResponse> {
        val ridesPage = repository.findAllByDriverId(
            driverId,
            PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortField))
        )
        return ridesPage.map(mapper::toRideResponse)
    }

    @Transactional(readOnly = true)
    override fun getRideById(driverId: Long, rideId: Long): RideResponse {
        val ride = getRideIfExists(rideId)
        validateDriver(ride, driverId)
        return mapper.toRideResponse(ride)
    }

    private fun checkRideCanBeRated(endTime: ZonedDateTime) {
        val duration = Duration.between(endTime, ZonedDateTime.now(ZoneId.of(TimeZone.getDefault().id))).toDays()
        if (duration > RideConstants.RATE_TIME_LIMIT_DAYS) {
            throw ActionNotAllowedException(ExceptionKeys.RATE_TIME_IS_OVER)
        }
    }

    private fun validateDriver(ride: Ride, driverId: Long) {
        if (ride.driverId != driverId) {
            throw EntityNotFoundException(ExceptionKeys.RIDE_NOT_FOUND_ID, ride.id!!)
        }
    }

    private fun getRideIfExists(rideId: Long): Ride {
        return repository.findById(rideId).orElseThrow {
            EntityNotFoundException(ExceptionKeys.RIDE_NOT_FOUND_ID, rideId)
        }
    }
}