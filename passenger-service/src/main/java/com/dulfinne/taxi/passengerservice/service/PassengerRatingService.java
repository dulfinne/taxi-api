package com.dulfinne.taxi.passengerservice.service;

import com.dulfinne.taxi.avro.Rating;
import com.dulfinne.taxi.passengerservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;

public interface PassengerRatingService {
  PaginatedResponse<PassengerRatingResponse> getPassengerRatings(
      String username, Integer offset, Integer limit, String sortField);

  void savePassengerRating(Rating rating);
}
