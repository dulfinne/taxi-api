package com.dulfinne.taxi.passengerservice.repository;

import com.dulfinne.taxi.passengerservice.model.PassengerRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRatingRepository extends JpaRepository<PassengerRating, Long> {
  List<PassengerRating> findByPassengerInfo_Id(Long passengerId);
}
