package com.dulfinne.taxi.driverservice.service.impl;

import com.dulfinne.taxi.driverservice.dto.request.CarRequest;
import static com.dulfinne.taxi.driverservice.mapper.CarMapper.CAR_MAPPER_INSTANCE;
import com.dulfinne.taxi.driverservice.dto.response.CarResponse;
import com.dulfinne.taxi.driverservice.model.Car;
import com.dulfinne.taxi.driverservice.repository.CarRepository;
import com.dulfinne.taxi.driverservice.service.CarService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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

  @Transactional(readOnly = true)
  @Override
  public Page<CarResponse> getAllCars(Integer offset, Integer limit, String sortField) {

    Page<Car> carsPage =
        carRepository.findAll(
            PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortField)));

    return carsPage.map(CAR_MAPPER_INSTANCE::toResponse);
  }

  @Transactional(readOnly = true)
  @Override
  public CarResponse getCarById(Long id) {
    Car car = getCarIfExist(id);
    return CAR_MAPPER_INSTANCE.toResponse(car);
  }

  @Transactional
  @Override
  public CarResponse saveCar(CarRequest request) {
    Car car = CAR_MAPPER_INSTANCE.toEntity(request);

    checkRegistrationNumberUniqueness(car.getRegistrationNumber());

    carRepository.save(car);
    return CAR_MAPPER_INSTANCE.toResponse(car);
  }

  @Transactional
  @Override
  public CarResponse updateCar(Long id, CarRequest request) {
    Car car = getCarIfExist(id);

    checkRegistrationNumberUniqueness(car.getRegistrationNumber(), request.registrationNumber());

    CAR_MAPPER_INSTANCE.updateEntity(request, car);

    carRepository.save(car);
    return CAR_MAPPER_INSTANCE.toResponse(car);
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
            () -> new EntityNotFoundException(String.format("Car not found: id = %d", id)));
  }

  private void checkRegistrationNumberUniqueness(String registrationNumber) {
    if (carRepository.findByRegistrationNumber(registrationNumber).isPresent()) {
      throw new EntityExistsException(
          String.format("Car with registration number %s already exists", registrationNumber));
    }
  }

  private void checkRegistrationNumberUniqueness(
      String registrationNumber, String updatedRegistrationNumber) {
    if (!updatedRegistrationNumber.equals(registrationNumber)
        && carRepository.findByRegistrationNumber(updatedRegistrationNumber).isPresent()) {
      throw new EntityExistsException(
          String.format("Car with registration number %s already exists", registrationNumber));
    }
  }
}
