package com.dulfinne.taxi.driverservice.service.impl;

import com.dulfinne.taxi.driverservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.driverservice.dto.request.DriverRatingRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverRatingResponse;
import com.dulfinne.taxi.driverservice.mapper.DriverRatingMapper;
import com.dulfinne.taxi.driverservice.model.Driver;
import com.dulfinne.taxi.driverservice.model.DriverRating;
import com.dulfinne.taxi.driverservice.repository.DriverRatingRepository;
import com.dulfinne.taxi.driverservice.repository.DriverRepository;
import com.dulfinne.taxi.driverservice.service.DriverRatingService;
import com.dulfinne.taxi.driverservice.util.ExceptionKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DriverRatingServiceImpl implements DriverRatingService {

  private final DriverRatingRepository ratingRepository;
  private final DriverRepository driverRepository;
  private final DriverRatingMapper driverRatingMapper;

  @Transactional(readOnly = true)
  @Override
  public Page<DriverRatingResponse> getAllDriverRatings(
      Long driverId, Integer offset, Integer limit, String sortField) {

    Page<DriverRating> driverRatingPage =
        ratingRepository.findByDriverId(
            driverId, PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortField)));

    return driverRatingPage.map(driverRatingMapper::toResponse);
  }

  @Transactional
  @Override
  public DriverRatingResponse saveDriverRating(
      Long driverId, DriverRatingRequest driverRatingRequest) {

    Driver driver = getDriverIfExist(driverId);
    DriverRating driverRating = driverRatingMapper.toEntity(driverRatingRequest);

    driverRating.setDriver(driver);
    driver.setNumberOfRatings(driver.getNumberOfRatings() + 1);
    driver.setSumOfRatings(driver.getSumOfRatings() + driverRating.getRating());
    ratingRepository.save(driverRating);

    return driverRatingMapper.toResponse(driverRating);
  }

  private Driver getDriverIfExist(Long id) {
    return driverRepository
        .findById(id)
        .orElseThrow(
            () ->
                new EntityNotFoundException(ExceptionKeys.DRIVER_NOT_FOUND_ID, id));
  }
}
