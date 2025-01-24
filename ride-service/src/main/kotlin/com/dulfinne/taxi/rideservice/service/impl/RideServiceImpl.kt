package com.dulfinne.taxi.rideservice.service.impl

import com.dulfinne.taxi.rideservice.dto.response.RideResponse
import com.dulfinne.taxi.rideservice.exception.EntityNotFoundException
import com.dulfinne.taxi.rideservice.exception.GlobalExceptionHandler
import com.dulfinne.taxi.rideservice.mapper.RideMapper
import com.dulfinne.taxi.rideservice.model.Ride
import com.dulfinne.taxi.rideservice.repository.RideRepository
import com.dulfinne.taxi.rideservice.service.RideService
import com.dulfinne.taxi.rideservice.util.ExceptionKeys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RideServiceImpl(val repository: RideRepository, val mapper: RideMapper) : RideService {
    private val log: Logger = LoggerFactory.getLogger(RideServiceImpl::class.java)

    @Transactional(readOnly = true)
    override fun getAllRides(offset: Int, limit: Int, sortField: String): Page<RideResponse> {
        log.info("Getting all rides. Started. Sort field = $sortField")
        val ridesPage = repository.findAll(
            PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortField))
        )

        return ridesPage.map(mapper::toRideResponse)
    }

    @Transactional(readOnly = true)
    override fun getRideById(rideId: Long): RideResponse {
        log.info("Getting ride by id. Started. Ride id = $rideId")
        val ride = getRideIfExists(rideId)
        return mapper.toRideResponse(ride)
    }

    @Transactional
    override fun deleteRideById(rideId: Long) {
        log.info("Deleting ride. Started. Ride id = $rideId")
        val ride = getRideIfExists(rideId)
        repository.delete(ride)
    }

    private fun getRideIfExists(rideId: Long): Ride {
        return repository.findById(rideId).orElseThrow {
            EntityNotFoundException(ExceptionKeys.RIDE_NOT_FOUND_ID, rideId)
        }
    }
}