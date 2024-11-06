package com.dulfinne.taxi.driverservice.service;

import com.dulfinne.taxi.driverservice.dto.request.DriverRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverResponse;
import org.springframework.data.domain.Page;

public interface DriverService {

  Page<DriverResponse> getAllDrivers(Integer offset, Integer limit, String sortField);

  DriverResponse getDriverById(Long id);

  DriverResponse saveDriver(String username, DriverRequest driverRequest);

  DriverResponse updateDriver(Long id, DriverRequest driverRequest);

  void deleteDriver(Long id);

  DriverResponse assignCarToDriver(Long driverId, Long carId);

  DriverResponse removeCarFromDriver(Long driverId, Long carId);
}
