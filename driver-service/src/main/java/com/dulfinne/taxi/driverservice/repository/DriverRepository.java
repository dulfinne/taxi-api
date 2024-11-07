package com.dulfinne.taxi.driverservice.repository;

import com.dulfinne.taxi.driverservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
  Optional<Driver> findByUsername(String username);

  Optional<Driver> findByPhoneNumber(String phoneNumber);

  Optional<Driver> findByCarId(Long carId);
}
