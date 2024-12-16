package com.dulfinne.taxi.driverservice.unit.service;

import com.dulfinne.taxi.driverservice.dto.request.DriverRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverResponse;
import com.dulfinne.taxi.driverservice.exception.EntityAlreadyExistsException;
import com.dulfinne.taxi.driverservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.driverservice.mapper.DriverMapper;
import com.dulfinne.taxi.driverservice.model.Car;
import com.dulfinne.taxi.driverservice.model.Driver;
import com.dulfinne.taxi.driverservice.repository.CarRepository;
import com.dulfinne.taxi.driverservice.repository.DriverRepository;
import com.dulfinne.taxi.driverservice.service.impl.DriverServiceImpl;
import com.dulfinne.taxi.driverservice.util.CarTestData;
import com.dulfinne.taxi.driverservice.util.DriverTestData;
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
public class DriverServiceTest {

  @InjectMocks private DriverServiceImpl driverService;
  @Mock private DriverRepository driverRepository;
  @Mock private CarRepository carRepository;
  @Spy private DriverMapper driverMapper = Mappers.getMapper(DriverMapper.class);

  @Test
  void getAllDrivers_whenValidParams_thenReturnDriversPage() {
    Driver driver = DriverTestData.getDriver();
    DriverResponse response = DriverTestData.getResponse();
    Page<Driver> driverPage = new PageImpl<>(List.of(driver, driver));
    Page<DriverResponse> responsePage = new PageImpl<>(List.of(response, response));

    // Arrange
    when(driverRepository.findAll(any(Pageable.class))).thenReturn(driverPage);
    when(driverMapper.toResponse(any(Driver.class))).thenReturn(response);

    // Act
    Page<DriverResponse> result =
        driverService.getAllDrivers(
            PaginationTestData.DEFAULT_OFFSET,
            PaginationTestData.DEFAULT_LIMIT,
            PaginationTestData.DRIVER_SORT_FIELD);

    // Assert
    assertEquals(responsePage.getContent(), result.getContent());
    assertEquals(driverPage.getTotalElements(), result.getTotalElements());
    assertEquals(driverPage.getNumber(), result.getNumber());
    assertEquals(driverPage.getSize(), result.getSize());

    verify(driverRepository, times(1)).findAll(any(Pageable.class));
    verify(driverMapper, times(responsePage.getNumberOfElements())).toResponse(any(Driver.class));
  }

  @Test
  void getDriverByUsername_whenValidParams_thenReturnDriverResponse() {
    Driver driver = DriverTestData.getDriver();
    String username = DriverTestData.USERNAME;
    DriverResponse response = DriverTestData.getResponse();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));
    when(driverMapper.toResponse(any(Driver.class))).thenReturn(response);

    // Act
    DriverResponse result = driverService.getDriverByUsername(username);

    // Arrange
    assertEquals(response, result);
    verify(driverRepository, times(1)).findByUsername(any(String.class));
    verify(driverMapper, times(1)).toResponse(any(Driver.class));
  }

  @Test
  void getDriverByUsername_whenDriverNotFound_thenThrowEntityNotFoundException() {
    String username = DriverTestData.USERNAME;

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> driverService.getDriverByUsername(username));
    verify(driverRepository, times(1)).findByUsername(any(String.class));
  }

  @Test
  void saveDriver_whenValidParams_thenReturnDriverResponse() {
    Driver driver = DriverTestData.getCreatedDriver();
    DriverResponse response = DriverTestData.getCreatedResponse();
    String username = DriverTestData.USERNAME;
    DriverRequest request = DriverTestData.getCreateRequest();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
    when(driverRepository.findByPhoneNumber(any(String.class))).thenReturn(Optional.empty());
    when(driverRepository.save(any(Driver.class))).thenReturn(driver);
    when(driverMapper.calculateAverageRating(
            DriverTestData.START_SUM_OF_RATINGS, DriverTestData.START_NUMBER_OF_RATINGS))
        .thenReturn(DriverTestData.START_AVERAGE_RATING);

    // Act
    DriverResponse result = driverService.saveDriver(username, request);

    // Assert
    assertEquals(response, result);

    verify(driverRepository, times(1)).findByUsername(any(String.class));
    verify(driverRepository, times(1)).findByPhoneNumber(any(String.class));
    verify(driverMapper, times(1)).toEntity(any(DriverRequest.class));
    verify(driverRepository, times(1)).save(any(Driver.class));
    verify(driverMapper, times(1)).toResponse(any(Driver.class));
  }

  @Test
  void saveDriver_whenSameUsernameExists_thenThrowEntityExistsException() {
    Driver driver = DriverTestData.getCreatedDriver();
    String username = DriverTestData.USERNAME;
    DriverRequest request = DriverTestData.getCreateRequest();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));

    // Act & Assert
    assertThrows(
        EntityAlreadyExistsException.class, () -> driverService.saveDriver(username, request));
  }

  @Test
  void saveDriver_whenSamePhoneNumberExists_thenThrowEntityExistsException() {
    Driver driver = DriverTestData.getCreatedDriver();
    String username = DriverTestData.USERNAME;
    DriverRequest request = DriverTestData.getCreateRequest();

    // Arrange
    when(driverRepository.findByPhoneNumber(any(String.class))).thenReturn(Optional.of(driver));

    // Act & Assert
    assertThrows(
        EntityAlreadyExistsException.class, () -> driverService.saveDriver(username, request));
  }

  @Test
  void updateDriver_whenUpdateToSameFields_thenReturnDriverResponse() {
    String username = DriverTestData.USERNAME;
    Driver driver = DriverTestData.getDriver();
    DriverRequest request = DriverTestData.getCreateRequest();
    DriverResponse response = DriverTestData.getResponse();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));
    when(driverRepository.save(any(Driver.class))).thenReturn(driver);
    when(driverMapper.calculateAverageRating(
            DriverTestData.SUM_OF_RATINGS, DriverTestData.NUMBER_OF_RATINGS))
        .thenReturn(DriverTestData.AVERAGE_RATING);

    // Act
    DriverResponse result = driverService.updateDriver(username, request);

    // Assert
    assertEquals(response, result);

    verify(driverRepository, times(1)).findByUsername(any(String.class));
    verify(driverMapper, times(1)).updateEntity(any(DriverRequest.class), any(Driver.class));
    verify(driverRepository, times(1)).save(any(Driver.class));
    verify(driverMapper, times(1)).toResponse(any(Driver.class));
  }

  @Test
  void updateDriver_whenUpdateToOtherFields_thenReturnDriverResponse() {
    String username = DriverTestData.USERNAME;
    Driver driver = DriverTestData.getDriver();
    DriverRequest request = DriverTestData.getUpdateRequest();
    DriverResponse response = DriverTestData.getUpdatedResponse();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));
    when(driverRepository.findByPhoneNumber(any(String.class))).thenReturn(Optional.empty());
    when(driverMapper.calculateAverageRating(
            DriverTestData.SUM_OF_RATINGS, DriverTestData.NUMBER_OF_RATINGS))
        .thenReturn(DriverTestData.AVERAGE_RATING);

    // Act
    DriverResponse result = driverService.updateDriver(username, request);

    // Assert
    assertEquals(response, result);

    verify(driverRepository, times(1)).findByUsername(any(String.class));
    verify(driverRepository, times(1)).findByPhoneNumber(any(String.class));
    verify(driverMapper, times(1)).updateEntity(any(DriverRequest.class), any(Driver.class));
    verify(driverRepository, times(1)).save(any(Driver.class));
    verify(driverMapper, times(1)).toResponse(any(Driver.class));
  }

  @Test
  void updateDriver_whenSamePhoneNumberDifferentUser_thenThrowEntityAlreadyExistsException() {
    String username = DriverTestData.USERNAME;
    Driver driver = DriverTestData.getDriver();
    DriverRequest request = DriverTestData.getUpdateRequest();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));
    when(driverRepository.findByPhoneNumber(any(String.class))).thenReturn(Optional.of(driver));

    // Act & Assert
    assertThrows(
        EntityAlreadyExistsException.class, () -> driverService.updateDriver(username, request));
  }

  @Test
  void updateDriver_whenDriverNotFound_thenThrowEntityNotFoundException() {
    String username = DriverTestData.USERNAME;
    DriverRequest request = DriverTestData.getUpdateRequest();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        EntityNotFoundException.class, () -> driverService.updateDriver(username, request));
  }

  @Test
  void deleteDriver_whenDriverWasFound_thenDeleteDriver() {
    String username = DriverTestData.USERNAME;
    Driver driver = DriverTestData.getDriver();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));

    // Act
    driverService.deleteDriver(username);

    // Assert
    verify(driverRepository, times(1)).findByUsername(username);
    verify(driverRepository, times(1)).delete(any(Driver.class));
  }

  @Test
  void deletePassenger_whenPassengerNotFound_thenThrowEntityNotFoundException() {
    String username = DriverTestData.USERNAME;

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> driverService.deleteDriver(username));
    verify(driverRepository, times(1)).findByUsername(username);
  }

  @Test
  void assignCarToDriver_whenValidParams_thenReturnDriverResponse() {
    String username = DriverTestData.USERNAME;
    Long carId = CarTestData.ID;
    Driver driver = DriverTestData.getDriver();
    Car car = CarTestData.getCar();
    //  Driver driverWithCar = DriverTestData.getDriverWithCar();
    DriverResponse expected = DriverTestData.getDriverWithCarResponse();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));
    when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
    when(driverRepository.findByCarId(any(Long.class))).thenReturn(Optional.empty());
    // when(driverRepository.save(any(Driver.class))).thenReturn(driverWithCar);
    when(driverMapper.calculateAverageRating(
            DriverTestData.SUM_OF_RATINGS, DriverTestData.NUMBER_OF_RATINGS))
        .thenReturn(DriverTestData.AVERAGE_RATING);

    // Act
    DriverResponse result = driverService.assignCarToDriver(username, carId);

    // Assert
    assertEquals(expected, result);
    assertEquals(expected.username(), result.username());

    verify(driverRepository, times(1)).findByUsername(any(String.class));
    verify(carRepository, times(1)).findById(any(Long.class));
    verify(driverRepository, times(1)).save(any(Driver.class));
    verify(driverMapper, times(1)).toResponse(any(Driver.class));
  }

  @Test
  void assignCarToDriver_whenDriverNotFound_thenThrowEntityNotFoundException() {
    String username = DriverTestData.USERNAME;
    Long carId = CarTestData.ID;

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        EntityNotFoundException.class, () -> driverService.assignCarToDriver(username, carId));
  }

  @Test
  void assignCarToDriver_whenCarNotFound_thenThrowEntityNotFoundException() {
    String username = DriverTestData.USERNAME;
    Long carId = CarTestData.ID;
    Driver driver = DriverTestData.getDriver();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));
    when(carRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        EntityNotFoundException.class, () -> driverService.assignCarToDriver(username, carId));
  }

  @Test
  void assignCarToDriver_whenCarAssignedToOtherDriver_thenThrowEntityAlreadyExistsException() {
    String username = DriverTestData.USERNAME;
    Long carId = CarTestData.ID;
    Driver driver = DriverTestData.getDriver();
    Car car = CarTestData.getCar();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));
    when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
    when(driverRepository.findByCarId(any(Long.class))).thenReturn(Optional.of(driver));

    // Act & Assert
    assertThrows(
        EntityAlreadyExistsException.class, () -> driverService.assignCarToDriver(username, carId));
  }

  @Test
  void removeCarFromDriver_whenValidParams_thenReturnDriverResponse() {
    String username = DriverTestData.USERNAME;
    Driver driverWithCar = DriverTestData.getDriverWithCar();
    DriverResponse expected = DriverTestData.getResponse();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driverWithCar));
    when(driverMapper.calculateAverageRating(
            DriverTestData.SUM_OF_RATINGS, DriverTestData.NUMBER_OF_RATINGS))
        .thenReturn(DriverTestData.AVERAGE_RATING);

    // Act
    DriverResponse result = driverService.removeCarFromDriver(username);

    // Assert
    assertEquals(expected, result);
    verify(driverRepository, times(1)).findByUsername(any(String.class));
    verify(driverRepository, times(1)).save(any(Driver.class));
    verify(driverMapper, times(1)).toResponse(any(Driver.class));
  }

  @Test
  void removeCarFromDriver_whenDriverNotFount_thenThrowsEntityNotFoundException() {
    String username = DriverTestData.USERNAME;

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> driverService.removeCarFromDriver(username));
  }
}
