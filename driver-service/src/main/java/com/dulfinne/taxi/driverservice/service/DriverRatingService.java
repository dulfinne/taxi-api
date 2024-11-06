package com.dulfinne.taxi.driverservice.service;

import com.dulfinne.taxi.driverservice.dto.request.DriverRatingRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverRatingResponse;
import org.springframework.data.domain.Page;

public interface DriverRatingService {

  Page<DriverRatingResponse> getAllDriverRatings(
      Long driverId, Integer offset, Integer limit, String sortField);

  DriverRatingResponse saveDriverRating(Long driverId, DriverRatingRequest driverRatingRequest);
}
