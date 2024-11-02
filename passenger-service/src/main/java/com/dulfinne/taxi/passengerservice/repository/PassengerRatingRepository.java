package com.dulfinne.taxi.passengerservice.repository;

import com.dulfinne.taxi.passengerservice.model.PassengerRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRatingRepository extends JpaRepository<PassengerRating, Long> {
  Page<PassengerRating> findByPassengerInfo_Id(Long passengerId, Pageable pageable);
}
