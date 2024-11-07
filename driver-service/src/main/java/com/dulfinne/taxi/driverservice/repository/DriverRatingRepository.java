package com.dulfinne.taxi.driverservice.repository;

import com.dulfinne.taxi.driverservice.model.DriverRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRatingRepository extends JpaRepository<DriverRating, Long> {
    Page<DriverRating> findByDriverId(Long driverId, Pageable pageable);
}
