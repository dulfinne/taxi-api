package com.dulfinne.taxi.driverservice.service.impl;

import com.dulfinne.taxi.driverservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.driverservice.exception.EntityAlreadyExistsException;
import com.dulfinne.taxi.driverservice.dto.request.CarRequest;
import com.dulfinne.taxi.driverservice.dto.response.CarResponse;
import com.dulfinne.taxi.driverservice.mapper.CarMapper;
import com.dulfinne.taxi.driverservice.model.Car;
import com.dulfinne.taxi.driverservice.repository.CarRepository;
import com.dulfinne.taxi.driverservice.service.CarService;
import com.dulfinne.taxi.driverservice.util.ExceptionKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

  private final CarRepository carRepository;
  private final CarMapper carMapper;

  @Transactional(readOnly = true)
  @Override
  public Page<CarResponse> getAllCars(Integer offset, Integer limit, String sortField) {
    Page<Car> carsPage =
        carRepository.findAll(
            PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortField)));

    return carsPage.map(carMapper::toResponse);
  }

  @Transactional(readOnly = true)
  @Override
  public CarResponse getCarById(Long id) {
    Car car = getCarIfExist(id);
    return carMapper.toResponse(car);
  }

  @Transactional
  @Override
  public CarResponse saveCar(CarRequest request) {
    Car car = carMapper.toEntity(request);
    checkRegistrationNumberUniqueness(car.getRegistrationNumber());
    return carMapper.toResponse(carRepository.save(car));
  }

  @Transactional
  @Override
  public CarResponse updateCar(Long id, CarRequest request) {
    Car car = getCarIfExist(id);

    checkRegistrationNumberUniqueness(car.getRegistrationNumber(), request.registrationNumber());

    carMapper.updateEntity(request, car);

    carRepository.save(car);
    return carMapper.toResponse(car);
  }

  @Transactional
  @Override
  public void deleteCar(Long id) {
    Car car = getCarIfExist(id);
    carRepository.delete(car);
  }

  private Car getCarIfExist(Long id) {
    return carRepository
        .findById(id)
        .orElseThrow(
            () -> new EntityNotFoundException(ExceptionKeys.CAR_NOT_FOUND_ID, id));
  }

  private void checkRegistrationNumberUniqueness(String registrationNumber) {
    if (carRepository.findByRegistrationNumber(registrationNumber).isPresent()) {
      throw new EntityAlreadyExistsException(ExceptionKeys.CAR_EXISTS_REGISTRATION_NUMBER, registrationNumber);
    }
  }

  private void checkRegistrationNumberUniqueness(
      String registrationNumber, String updatedRegistrationNumber) {
    if (!updatedRegistrationNumber.equals(registrationNumber)
        && carRepository.findByRegistrationNumber(updatedRegistrationNumber).isPresent()) {
      throw new EntityAlreadyExistsException(ExceptionKeys.CAR_EXISTS_REGISTRATION_NUMBER, updatedRegistrationNumber);
    }
  }
}
