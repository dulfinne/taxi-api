package com.dulfinne.taxi.passengerservice.service;

import com.dulfinne.taxi.passengerservice.model.PassengerRating;

import java.util.List;

public interface PassengerRatingService {

    List<PassengerRating> getPassengerRatings(Long passengerId);

    PassengerRating savePassengerRating(Long passengerId, PassengerRating passengerRating);
}
