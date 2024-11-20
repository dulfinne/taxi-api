package com.dulfinne.taxi.passengerservice.repository;

import com.dulfinne.taxi.passengerservice.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
  Optional<Passenger> findByPhoneNumber(String phoneNumber);
  Optional<Passenger> findByUsername(String username);
}
