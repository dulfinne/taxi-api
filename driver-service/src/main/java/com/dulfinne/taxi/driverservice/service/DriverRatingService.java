package com.dulfinne.taxi.driverservice.service;

import com.dulfinne.taxi.driverservice.dto.request.DriverRatingRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverRatingResponse;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface DriverRatingService {

  Page<DriverRatingResponse> getAllDriverRatings(
      String username, Integer offset, Integer limit, String sortField);

  DriverRatingResponse saveDriverRating(String username, DriverRatingRequest driverRatingRequest);
}
