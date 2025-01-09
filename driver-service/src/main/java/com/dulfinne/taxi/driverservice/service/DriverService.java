package com.dulfinne.taxi.driverservice.service;

import com.dulfinne.taxi.driverservice.dto.request.DriverRequest;
import com.dulfinne.taxi.driverservice.dto.request.PointRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverResponse;
import com.dulfinne.taxi.driverservice.dto.response.PointResponse;
import org.springframework.data.domain.Page;

public interface DriverService {

  Page<DriverResponse> getAllDrivers(Integer offset, Integer limit, String sortField);

  DriverResponse getDriverByUsername(String username);

  DriverResponse saveDriver(String username, DriverRequest driverRequest);

  DriverResponse updateDriver(String username, DriverRequest driverRequest);

  void deleteDriver(String username);

  DriverResponse assignCarToDriver(String username, Long carId);

  DriverResponse removeCarFromDriver(String username);

  PointResponse getDriverLocation(String username);

  PointResponse updateDriverLocation(String username, PointRequest request);
}
