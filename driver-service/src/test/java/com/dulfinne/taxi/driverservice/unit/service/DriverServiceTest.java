package com.dulfinne.taxi.driverservice.unit.service;

import com.dulfinne.taxi.driverservice.dto.request.DriverRequest;
import com.dulfinne.taxi.driverservice.dto.response.CarResponse;
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
class DriverServiceTest {

  @InjectMocks private DriverServiceImpl driverService;

  @Mock private DriverRepository driverRepository;
  @Mock private CarRepository carRepository;
  @Spy private DriverMapper driverMapper = Mappers.getMapper(DriverMapper.class);

  @Test
  void getAllDrivers_whenValidParams_thenReturnDriversPage() {
    Driver driver = DriverTestData.getDriver().build();
    DriverResponse response = DriverTestData.getResponse().build();
    Page<Driver> driverPage = new PageImpl<>(List.of(driver, driver));
    Page<DriverResponse> responsePage = new PageImpl<>(List.of(response, response));

    // Arrange
    when(driverRepository.findAll(any(Pageable.class))).thenReturn(driverPage);
    when(driverMapper.toResponse(any(Driver.class))).thenReturn(response);

    // Act
    Page<DriverResponse> actualPage =
        driverService.getAllDrivers(
            PaginationTestData.DEFAULT_OFFSET,
            PaginationTestData.DEFAULT_LIMIT,
            PaginationTestData.DRIVER_SORT_FIELD);

    // Assert
    assertEquals(responsePage.getContent(), actualPage.getContent());
    assertEquals(driverPage.getTotalElements(), actualPage.getTotalElements());
    assertEquals(driverPage.getNumber(), actualPage.getNumber());
    assertEquals(driverPage.getSize(), actualPage.getSize());

    verify(driverRepository).findAll(any(Pageable.class));
    verify(driverMapper, times(2)).toResponse(any(Driver.class));
  }

  @Test
  void getDriverByUsername_whenValidParams_thenReturnDriverResponse() {
    Driver driver = DriverTestData.getDriver().build();
    String username = DriverTestData.USERNAME;
    DriverResponse response = DriverTestData.getResponse().build();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));
    when(driverMapper.toResponse(any(Driver.class))).thenReturn(response);

    // Act
    DriverResponse actual = driverService.getDriverByUsername(username);

    // Arrange
    assertEquals(response, actual);
    verify(driverRepository).findByUsername(any(String.class));
    verify(driverMapper).toResponse(any(Driver.class));
  }

  @Test
  void getDriverByUsername_whenDriverNotFound_thenThrowEntityNotFoundException() {
    String username = DriverTestData.USERNAME;

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> driverService.getDriverByUsername(username));
    verify(driverRepository).findByUsername(any(String.class));
  }

  @Test
  void saveDriver_whenValidParams_thenReturnDriverResponse() {
    Driver driver =
        DriverTestData.getDriver()
            .sumOfRatings(DriverTestData.START_SUM_OF_RATINGS)
            .numberOfRatings(DriverTestData.START_NUMBER_OF_RATINGS)
            .build();
    DriverResponse response =
        DriverTestData.getResponse().averageRating(DriverTestData.START_AVERAGE_RATING).build();
    String username = DriverTestData.USERNAME;
    DriverRequest request = DriverTestData.getCreateRequest().build();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
    when(driverRepository.findByPhoneNumber(any(String.class))).thenReturn(Optional.empty());
    when(driverRepository.save(any(Driver.class))).thenReturn(driver);
    when(driverMapper.calculateAverageRating(
            DriverTestData.START_SUM_OF_RATINGS, DriverTestData.START_NUMBER_OF_RATINGS))
        .thenReturn(DriverTestData.START_AVERAGE_RATING);

    // Act
    DriverResponse actual = driverService.saveDriver(username, request);

    // Assert
    assertEquals(response, actual);

    verify(driverRepository).findByUsername(any(String.class));
    verify(driverRepository).findByPhoneNumber(any(String.class));
    verify(driverMapper).toEntity(any(DriverRequest.class));
    verify(driverRepository).save(any(Driver.class));
    verify(driverMapper).toResponse(any(Driver.class));
  }

  @Test
  void saveDriver_whenSameUsernameExists_thenThrowEntityExistsException() {
    Driver driver =
        DriverTestData.getDriver()
            .sumOfRatings(DriverTestData.START_SUM_OF_RATINGS)
            .numberOfRatings(DriverTestData.START_NUMBER_OF_RATINGS)
            .build();
    String username = DriverTestData.USERNAME;
    DriverRequest request = DriverTestData.getCreateRequest().build();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));

    // Act & Assert
    assertThrows(
        EntityAlreadyExistsException.class, () -> driverService.saveDriver(username, request));
  }

  @Test
  void saveDriver_whenSamePhoneNumberExists_thenThrowEntityExistsException() {
    Driver driver =
        DriverTestData.getDriver()
            .sumOfRatings(DriverTestData.START_SUM_OF_RATINGS)
            .numberOfRatings(DriverTestData.START_NUMBER_OF_RATINGS)
            .build();
    String username = DriverTestData.USERNAME;
    DriverRequest request = DriverTestData.getCreateRequest().build();

    // Arrange
    when(driverRepository.findByPhoneNumber(any(String.class))).thenReturn(Optional.of(driver));

    // Act & Assert
    assertThrows(
        EntityAlreadyExistsException.class, () -> driverService.saveDriver(username, request));
  }

  @Test
  void updateDriver_whenUpdateToSameFields_thenReturnDriverResponse() {
    String username = DriverTestData.USERNAME;
    Driver driver = DriverTestData.getDriver().build();
    DriverRequest request = DriverTestData.getCreateRequest().build();
    DriverResponse response = DriverTestData.getResponse().build();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));
    when(driverRepository.save(any(Driver.class))).thenReturn(driver);
    when(driverMapper.calculateAverageRating(
            DriverTestData.SUM_OF_RATINGS, DriverTestData.NUMBER_OF_RATINGS))
        .thenReturn(DriverTestData.AVERAGE_RATING);

    // Act
    DriverResponse actual = driverService.updateDriver(username, request);

    // Assert
    assertEquals(response, actual);

    verify(driverRepository).findByUsername(any(String.class));
    verify(driverMapper).updateEntity(any(DriverRequest.class), any(Driver.class));
    verify(driverRepository).save(any(Driver.class));
    verify(driverMapper).toResponse(any(Driver.class));
  }

  @Test
  void updateDriver_whenUpdateToOtherFields_thenReturnDriverResponse() {
    String username = DriverTestData.USERNAME;
    Driver driver = DriverTestData.getDriver().build();
    DriverRequest request = DriverTestData.getUpdateRequest().build();
    DriverResponse response = DriverTestData.getUpdatedResponse().build();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));
    when(driverRepository.findByPhoneNumber(any(String.class))).thenReturn(Optional.empty());
    when(driverMapper.calculateAverageRating(
            DriverTestData.SUM_OF_RATINGS, DriverTestData.NUMBER_OF_RATINGS))
        .thenReturn(DriverTestData.AVERAGE_RATING);

    // Act
    DriverResponse actual = driverService.updateDriver(username, request);

    // Assert
    assertEquals(response, actual);

    verify(driverRepository).findByUsername(any(String.class));
    verify(driverRepository).findByPhoneNumber(any(String.class));
    verify(driverMapper).updateEntity(any(DriverRequest.class), any(Driver.class));
    verify(driverRepository).save(any(Driver.class));
    verify(driverMapper).toResponse(any(Driver.class));
  }

  @Test
  void updateDriver_whenSamePhoneNumberDifferentUser_thenThrowEntityAlreadyExistsException() {
    String username = DriverTestData.USERNAME;
    Driver driver = DriverTestData.getDriver().build();
    DriverRequest request = DriverTestData.getUpdateRequest().build();

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
    DriverRequest request = DriverTestData.getUpdateRequest().build();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
    // Act & Assert
    assertThrows(
        EntityNotFoundException.class, () -> driverService.updateDriver(username, request));
  }

  @Test
  void deleteDriver_whenDriverWasFound_thenDeleteDriver() {
    String username = DriverTestData.USERNAME;
    Driver driver = DriverTestData.getDriver().build();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));

    // Act
    driverService.deleteDriver(username);

    // Assert
    verify(driverRepository).findByUsername(username);
    verify(driverRepository).delete(any(Driver.class));
  }

  @Test
  void deletePassenger_whenPassengerNotFound_thenThrowEntityNotFoundException() {
    String username = DriverTestData.USERNAME;

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> driverService.deleteDriver(username));
    verify(driverRepository).findByUsername(username);
  }

  @Test
  void assignCarToDriver_whenValidParams_thenReturnDriverResponse() {
    String username = DriverTestData.USERNAME;
    Long carId = CarTestData.ID;
    Car car = CarTestData.getCar().build();
    CarResponse carResponse = CarTestData.getResponse().build();
    Driver driver = DriverTestData.getDriver().build();
    DriverResponse expected = DriverTestData.getResponse().car(carResponse).build();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));
    when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
    when(driverRepository.findByCarId(any(Long.class))).thenReturn(Optional.empty());
    when(driverMapper.calculateAverageRating(
            DriverTestData.SUM_OF_RATINGS, DriverTestData.NUMBER_OF_RATINGS))
        .thenReturn(DriverTestData.AVERAGE_RATING);

    // Act
    DriverResponse actual = driverService.assignCarToDriver(username, carId);

    // Assert
    assertEquals(expected, actual);
    assertEquals(expected.username(), actual.username());

    verify(driverRepository).findByUsername(any(String.class));
    verify(carRepository).findById(any(Long.class));
    verify(driverRepository).save(any(Driver.class));
    verify(driverMapper).toResponse(any(Driver.class));
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
    Driver driver = DriverTestData.getDriver().build();

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
    Driver driver = DriverTestData.getDriver().build();
    Car car = CarTestData.getCar().build();

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
    Car car = CarTestData.getCar().build();
    Driver driverWithCar = DriverTestData.getDriver().car(car).build();
    DriverResponse expected = DriverTestData.getResponse().build();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driverWithCar));
    when(driverMapper.calculateAverageRating(
            DriverTestData.SUM_OF_RATINGS, DriverTestData.NUMBER_OF_RATINGS))
        .thenReturn(DriverTestData.AVERAGE_RATING);

    // Act
    DriverResponse actual = driverService.removeCarFromDriver(username);

    // Assert
    assertEquals(expected, actual);
    verify(driverRepository).findByUsername(any(String.class));
    verify(driverRepository).save(any(Driver.class));
    verify(driverMapper).toResponse(any(Driver.class));
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
