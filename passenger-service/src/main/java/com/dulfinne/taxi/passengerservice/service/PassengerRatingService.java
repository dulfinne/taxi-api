package com.dulfinne.taxi.passengerservice.service;

import com.dulfinne.taxi.avro.Rating;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import org.springframework.data.domain.Page;

public interface PassengerRatingService {
  Page<PassengerRatingResponse> getPassengerRatings(
      String username, Integer offset, Integer limit, String sortField);

  void savePassengerRating(Rating rating);
}
