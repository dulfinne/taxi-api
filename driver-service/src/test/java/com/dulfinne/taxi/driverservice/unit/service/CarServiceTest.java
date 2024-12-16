package com.dulfinne.taxi.driverservice.unit.service;

import com.dulfinne.taxi.driverservice.dto.request.CarRequest;
import com.dulfinne.taxi.driverservice.dto.response.CarResponse;
import com.dulfinne.taxi.driverservice.exception.EntityAlreadyExistsException;
import com.dulfinne.taxi.driverservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.driverservice.mapper.CarMapper;
import com.dulfinne.taxi.driverservice.model.Car;
import com.dulfinne.taxi.driverservice.repository.CarRepository;
import com.dulfinne.taxi.driverservice.service.impl.CarServiceImpl;
import com.dulfinne.taxi.driverservice.util.CarTestData;
import com.dulfinne.taxi.driverservice.util.PaginationTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
  @InjectMocks private CarServiceImpl carService;
  @Mock private CarRepository carRepository;
  @Spy private CarMapper carMapper = Mappers.getMapper(CarMapper.class);

  @Test
  void getAllCars_whenValidRequest_thenReturnCarResponsePage() {
    Car car = CarTestData.getCar();
    CarResponse carResponse = CarTestData.getResponse();
    Page<Car> carPage = new PageImpl<>(List.of(car, car));
    Page<CarResponse> expectedPage = new PageImpl<>(List.of(carResponse, carResponse));

    // Arrange
    when(carRepository.findAll(any(Pageable.class))).thenReturn(carPage);

    // Act
    Page<CarResponse> resultPage =
        carService.getAllCars(
            PaginationTestData.DEFAULT_OFFSET,
            PaginationTestData.DEFAULT_LIMIT,
            PaginationTestData.CAR_SORT_FIELD);

    // Assert
    assertEquals(expectedPage, resultPage);
    assertEquals(expectedPage.getTotalElements(), resultPage.getTotalElements());
    assertEquals(expectedPage.getContent(), resultPage.getContent());
    verify(carRepository, times(1)).findAll(any(Pageable.class));
    verify(carMapper, times(expectedPage.getNumberOfElements())).toResponse(any(Car.class));
  }

  @Test
  void getCarById_whenValidRequest_thenReturnCarResponse() {
    Long carId = CarTestData.ID;
    Car car = CarTestData.getCar();
    CarResponse expected = CarTestData.getResponse();

    // Arrange
    when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));

    // Act
    CarResponse carResponse = carService.getCarById(carId);

    // Assert
    assertEquals(expected, carResponse);
    verify(carRepository, times(1)).findById(any(Long.class));
    verify(carMapper, times(1)).toResponse(any(Car.class));
  }

  @Test
  void getCarById_whenCarNotFoundById_thenThrowEntityNotFoundException() {
    Long carId = CarTestData.ID;

    // Arrange
    when(carRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> carService.getCarById(carId));

    verify(carRepository, times(1)).findById(any(Long.class));
  }

  @Test
  void saveCar_whenValidRequest_thenReturnCarResponse() {
    CarRequest request = CarTestData.getRequest();
    Car car = CarTestData.getCar();
    CarResponse expected = CarTestData.getResponse();

    // Arrange
    when(carRepository.findByRegistrationNumber(any(String.class))).thenReturn(Optional.empty());
    when(carRepository.save(any(Car.class))).thenReturn(car);

    // Act
    CarResponse result = carService.saveCar(request);

    // Assert
    assertEquals(expected, result);
    verify(carRepository, times(1)).findByRegistrationNumber(any(String.class));
    verify(carRepository, times(1)).save(any(Car.class));
    verify(carMapper, times(1)).toResponse(any(Car.class));
    verify(carMapper, times(1)).toEntity(any(CarRequest.class));
  }

  @Test
  void saveCar_whenRegistrationNumberNotUnique_thenThrowEntityAlreadyExistsException() {
    CarRequest request = CarTestData.getRequest();
    Car car = CarTestData.getCar();

    // Arrange
    when(carRepository.findByRegistrationNumber(any(String.class))).thenReturn(Optional.of(car));

    // Act & Assert
    assertThrows(EntityAlreadyExistsException.class, () -> carService.saveCar(request));
  }

  @Test
  void updateCar_whenUpdateToSameFields_thenReturnCarResponse() {
    Long carId = CarTestData.ID;
    CarRequest request = CarTestData.getRequest();
    Car car = CarTestData.getCar();
    CarResponse expected = CarTestData.getResponse();

    // Arrange
    when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
    when(carRepository.save(any(Car.class))).thenReturn(car);

    // Act
    CarResponse result = carService.updateCar(carId, request);

    // Assert
    assertEquals(expected, result);
    verify(carRepository, times(1)).save(any(Car.class));
    verify(carMapper, times(1)).toResponse(any(Car.class));
    verify(carMapper, times(1)).updateEntity(any(CarRequest.class), any(Car.class));
  }

  @Test
  void updateCar_whenUpdateToOtherFields_thenReturnCarResponse() {
    Long carId = CarTestData.ID;
    CarRequest request = CarTestData.getUpdateRequest();
    Car car = CarTestData.getCar();
    CarResponse expected = CarTestData.getUpdateResponse();

    // Arrange
    when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
    when(carRepository.findByRegistrationNumber(any(String.class))).thenReturn(Optional.empty());
    when(carRepository.save(any(Car.class))).thenReturn(car);

    // Act
    CarResponse result = carService.updateCar(carId, request);

    // Assert
    assertEquals(expected, result);
    verify(carRepository, times(1)).findByRegistrationNumber(any(String.class));
    verify(carRepository, times(1)).save(any(Car.class));
    verify(carMapper, times(1)).toResponse(any(Car.class));
    verify(carMapper, times(1)).updateEntity(any(CarRequest.class), any(Car.class));
  }

  @Test
  void
      updateCar_whenUpdateToOtherFieldsAndRegistrationNumberExists_thenThrowEntityAlreadyExistsException() {
    Long carId = CarTestData.ID;
    CarRequest request = CarTestData.getUpdateRequest();
    Car car = CarTestData.getCar();

    // Arrange
    when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
    when(carRepository.findByRegistrationNumber(any(String.class))).thenReturn(Optional.of(car));

    // Act & Assert
    assertThrows(EntityAlreadyExistsException.class, () -> carService.updateCar(carId, request));

    verify(carRepository, times(1)).findByRegistrationNumber(any(String.class));
    verify(carRepository, times(1)).findById(any(Long.class));
  }

  @Test
  void updateCar_whenCarNotFound_thenThrowEntityNotFoundException() {
    Long carId = CarTestData.ID;
    CarRequest request = CarTestData.getUpdateRequest();

    // Arrange
    when(carRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> carService.updateCar(carId, request));

    verify(carRepository, times(1)).findById(any(Long.class));
  }

  @Test
  void deleteCar_whenValidParams_thenDeleteCar() {
    Long carId = CarTestData.ID;
    Car car = CarTestData.getCar();

    // Arrange
    when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));

    // Act
    carService.deleteCar(carId);

    // Assert
    verify(carRepository, times(1)).findById(any(Long.class));
  }

  @Test
  void deleteCar_whenCarNotFound_thenThrowEntityNotFoundException() {
    Long carId = CarTestData.ID;

    // Arrange
    when(carRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> carService.deleteCar(carId));

    verify(carRepository, times(1)).findById(any(Long.class));
  }
}
