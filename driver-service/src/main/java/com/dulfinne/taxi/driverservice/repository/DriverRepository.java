package com.dulfinne.taxi.driverservice.repository;

import com.dulfinne.taxi.driverservice.model.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

  @Query("SELECT t FROM Driver t LEFT JOIN FETCH t.car")
  Page<Driver> findAllWithCars(Pageable pageable);

  Optional<Driver> findByUsername(String username);

  Optional<Driver> findByPhoneNumber(String phoneNumber);

  Optional<Driver> findByCarId(Long carId);
}
