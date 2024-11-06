package com.dulfinne.taxi.driverservice.service.impl;

import static com.dulfinne.taxi.driverservice.mapper.DriverRatingMapper.RATING_MAPPER_INSTANCE;

import com.dulfinne.taxi.driverservice.dto.request.DriverRatingRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverRatingResponse;
import com.dulfinne.taxi.driverservice.model.Driver;
import com.dulfinne.taxi.driverservice.model.DriverRating;
import com.dulfinne.taxi.driverservice.repository.DriverRatingRepository;
import com.dulfinne.taxi.driverservice.repository.DriverRepository;
import com.dulfinne.taxi.driverservice.service.DriverRatingService;
import jakarta.persistence.EntityNotFoundException;
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

  @Transactional(readOnly = true)
  @Override
  public Page<DriverRatingResponse> getAllDriverRatings(
      Long driverId, Integer offset, Integer limit, String sortField) {

    Page<DriverRating> driverRatingPage =
        ratingRepository.findByDriverId(
            driverId, PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortField)));

    return driverRatingPage.map(RATING_MAPPER_INSTANCE::toResponse);
  }

  @Transactional
  @Override
  public DriverRatingResponse saveDriverRating(
      Long driverId, DriverRatingRequest driverRatingRequest) {

    Driver driver = getDriverIfExist(driverId);
    DriverRating driverRating = RATING_MAPPER_INSTANCE.toEntity(driverRatingRequest);

    driverRating.setDriver(driver);
    driver.setNumberOfRatings(driver.getNumberOfRatings() + 1);
    driver.setSumOfRatings(driver.getSumOfRatings() + driverRating.getRating());
    ratingRepository.save(driverRating);

    return RATING_MAPPER_INSTANCE.toResponse(driverRating);
  }

  private Driver getDriverIfExist(Long id) {
    return driverRepository
        .findById(id)
        .orElseThrow(
            () -> new EntityNotFoundException(String.format("Driver with id %d not found", id)));
  }
}
