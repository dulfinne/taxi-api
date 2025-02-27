package com.dulfinne.taxi.passengerservice.service;

import com.dulfinne.taxi.avro.Rating;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;

import java.util.List;

public interface PassengerRatingService {
  List<PassengerRatingResponse> getPassengerRatings(
      String username, Integer offset, Integer limit, String sortField);

  void savePassengerRating(Rating rating);
}
