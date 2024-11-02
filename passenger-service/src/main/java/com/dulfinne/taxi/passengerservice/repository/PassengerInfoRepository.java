package com.dulfinne.taxi.passengerservice.repository;

import com.dulfinne.taxi.passengerservice.model.PassengerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerInfoRepository extends JpaRepository<PassengerInfo, Long> {
  Optional<PassengerInfo> findByPhoneNumber(String phoneNumber);
  Optional<PassengerInfo> findByPassengerId(Long passenger_id);
}
