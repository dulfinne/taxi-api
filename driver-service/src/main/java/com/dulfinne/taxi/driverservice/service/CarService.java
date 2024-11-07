package com.dulfinne.taxi.driverservice.service;

import com.dulfinne.taxi.driverservice.dto.request.CarRequest;
import com.dulfinne.taxi.driverservice.dto.response.CarResponse;
import com.dulfinne.taxi.driverservice.model.Car;
import org.springframework.data.domain.Page;

public interface CarService {

  Page<CarResponse> getAllCars(Integer offset, Integer limit, String sortField);

  CarResponse getCarById(Long id);

  CarResponse saveCar(CarRequest request);

  CarResponse updateCar(Long id, CarRequest request);

  void deleteCar(Long id);
}
