package com.dulfinne.taxi.passengerservice.service;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRatingRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import org.springframework.data.domain.Page;

public interface PassengerRatingService {

  Page<PassengerRatingResponse> getPassengerRatings(
      Long passengerId, Integer offset, Integer limit, String sortField);

  PassengerRatingResponse savePassengerRating(
      Long passengerId, PassengerRatingRequest passengerRating);
}
