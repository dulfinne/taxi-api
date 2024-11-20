package com.dulfinne.taxi.passengerservice.service;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRatingRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface PassengerRatingService {
  Page<PassengerRatingResponse> getPassengerRatings(
          String username, Integer offset, Integer limit, String sortField);

  PassengerRatingResponse savePassengerRating(String username, PassengerRatingRequest request);
}
