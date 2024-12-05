package com.dulfinne.taxi.driverservice.service;

import com.dulfinne.taxi.avro.Rating;
import com.dulfinne.taxi.driverservice.dto.response.DriverRatingResponse;
import org.springframework.data.domain.Page;

public interface DriverRatingService {

  Page<DriverRatingResponse> getAllDriverRatings(
      String username, Integer offset, Integer limit, String sortField);

    void saveDriverRating(Rating rating);
}
