package com.dulfinne.taxi.passengerservice.unit.service;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import com.dulfinne.taxi.passengerservice.exception.EntityAlreadyExistsException;
import com.dulfinne.taxi.passengerservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.passengerservice.exception.IllegalSortFieldException;
import com.dulfinne.taxi.passengerservice.model.Passenger;
import com.dulfinne.taxi.passengerservice.repository.PassengerRepository;
import com.dulfinne.taxi.passengerservice.service.impl.PassengerServiceImpl;
import com.dulfinne.taxi.passengerservice.util.PaginationTestData;
import com.dulfinne.taxi.passengerservice.util.PassengerConstants;
import com.dulfinne.taxi.passengerservice.util.PassengerTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
class PassengerServiceTest {

  @InjectMocks private PassengerServiceImpl passengerService;

  @Mock private PassengerRepository passengerRepository;

  @Test
  void getAllPassengers_whenValidParams_thenReturnPassengersPage() {
    List<PassengerResponse> expectedContent = PassengerTestData.getResponseList();
    Page<Passenger> passengersPage = new PageImpl<>(PassengerTestData.getPassengerList());

    // Arrange
    when(passengerRepository.findAll(any(Pageable.class))).thenReturn(passengersPage);

    // Act
    Page<PassengerResponse> actualPage =
        passengerService.getAllPassengers(
            PaginationTestData.DEFAULT_OFFSET,
            PaginationTestData.DEFAULT_LIMIT,
            PaginationTestData.PASSENGER_SORT_FIELD);

    // Assert
    assertEquals(expectedContent, actualPage.getContent());
    assertEquals(passengersPage.getTotalElements(), actualPage.getTotalElements());
    assertEquals(passengersPage.getNumber(), actualPage.getNumber());
    assertEquals(passengersPage.getSize(), actualPage.getSize());

    verify(passengerRepository).findAll(any(Pageable.class));
  }

  @Test
  void getAllPassengers_whenInvalidSortField_thenThrowIllegalSortFieldException() {
    // Act & Assert
    assertThrows(
        IllegalSortFieldException.class,
        () ->
            passengerService.getAllPassengers(
                PaginationTestData.DEFAULT_OFFSET,
                PaginationTestData.DEFAULT_LIMIT,
                PaginationTestData.INVALID_SORT_FIELD));
  }

  @Test
  void getPassengerByUsername_whenPassengerExists_thenReturnPassengerResponse() {
    String username = PassengerTestData.FIRST_USERNAME;
    Passenger passenger = PassengerTestData.getFirst().build();
    PassengerResponse expected = PassengerTestData.getFirstResponse().build();

    // Arrange
    when(passengerRepository.findByUsername(username)).thenReturn(Optional.of(passenger));

    // Act
    PassengerResponse actual = passengerService.getPassengerByUsername(username);

    // Assert
    assertEquals(expected, actual);
    verify(passengerRepository).findByUsername(passenger.getUsername());
  }

  @Test
  void getPassengerByUsername_whenPassengerNotExists_thenReturnPassengerResponse() {
    // Arrange
    when(passengerRepository.findByUsername(PassengerTestData.FIRST_USERNAME))
        .thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        EntityNotFoundException.class,
        () -> passengerService.getPassengerByUsername(PassengerTestData.FIRST_USERNAME));
  }

  @Test
  void savePassenger_whenValidRequest_thenReturnPassengerResponse() {
    String username = PassengerTestData.FIRST_USERNAME;
    PassengerRequest request = PassengerTestData.getFirstRequest().build();
    Passenger passenger =
        PassengerTestData.getFirst()
            .rideCount(PassengerConstants.START_RIDE_COUNT)
            .sumOfRatings(PassengerConstants.START_SUM_OF_RATINGS)
            .numberOfRatings(PassengerConstants.START_NUMBER_OF_RATINGS)
            .build();
    PassengerResponse expected =
        PassengerTestData.getFirstResponse()
            .rideCount(PassengerConstants.START_RIDE_COUNT)
            .averageRating(PassengerTestData.START_AVERAGE_RATING)
            .build();

    // Arrange
    when(passengerRepository.findByUsername(PassengerTestData.FIRST_USERNAME))
        .thenReturn(Optional.empty());
    when(passengerRepository.findByPhoneNumber(PassengerTestData.FIRST_PHONE_NUMBER))
        .thenReturn(Optional.empty());
    when(passengerRepository.save(any(Passenger.class))).thenReturn(passenger);

    // Act
    PassengerResponse actual = passengerService.savePassenger(username, request);

    // Assert
    assertEquals(expected, actual);
    verify(passengerRepository).findByUsername(PassengerTestData.FIRST_USERNAME);
    verify(passengerRepository).findByPhoneNumber(PassengerTestData.FIRST_PHONE_NUMBER);
    verify(passengerRepository).save(any(Passenger.class));
  }

  @Test
  void savePassenger_whenDuplicateUsername_thenReturnEntityAlreadyExistsException() {
    String username = PassengerTestData.FIRST_USERNAME;
    PassengerRequest request = PassengerTestData.getFirstRequest().build();
    Passenger passenger =
        PassengerTestData.getFirst()
            .rideCount(PassengerConstants.START_RIDE_COUNT)
            .sumOfRatings(PassengerConstants.START_SUM_OF_RATINGS)
            .numberOfRatings(PassengerConstants.START_NUMBER_OF_RATINGS)
            .build();

    // Arrange
    when(passengerRepository.findByUsername(PassengerTestData.FIRST_USERNAME))
        .thenReturn(Optional.of(passenger));

    // Act & Assert
    assertThrows(
        EntityAlreadyExistsException.class,
        () -> passengerService.savePassenger(username, request));
  }

  @Test
  void savePassenger_whenDuplicatePhoneNumber_thenReturnEntityAlreadyExistsException() {
    String username = PassengerTestData.FIRST_USERNAME;
    PassengerRequest request = PassengerTestData.getFirstRequest().build();
    Passenger passenger =
        PassengerTestData.getFirst()
            .rideCount(PassengerConstants.START_RIDE_COUNT)
            .sumOfRatings(PassengerConstants.START_SUM_OF_RATINGS)
            .numberOfRatings(PassengerConstants.START_NUMBER_OF_RATINGS)
            .build();

    // Arrange
    when(passengerRepository.findByUsername(PassengerTestData.FIRST_USERNAME))
        .thenReturn(Optional.empty());
    when(passengerRepository.findByPhoneNumber(PassengerTestData.FIRST_PHONE_NUMBER))
        .thenReturn(Optional.of(passenger));

    // Act & Assert
    assertThrows(
        EntityAlreadyExistsException.class,
        () -> passengerService.savePassenger(username, request));
  }

  @Test
  void updatePassenger_whenUpdateToSameFields_thenReturnPassengerResponse() {
    String username = PassengerTestData.FIRST_USERNAME;
    PassengerRequest request = PassengerTestData.getFirstRequest().build();
    Passenger passenger = PassengerTestData.getFirst().build();

    PassengerResponse expected = PassengerTestData.getFirstResponse().build();

    // Arrange
    when(passengerRepository.findByUsername(username)).thenReturn(Optional.of(passenger));
    when(passengerRepository.save(any(Passenger.class))).thenReturn(passenger);

    // Act
    PassengerResponse actual = passengerService.updatePassenger(username, request);

    // Assert
    assertEquals(expected, actual);
    verify(passengerRepository).findByUsername(PassengerTestData.FIRST_USERNAME);
    verify(passengerRepository).save(any(Passenger.class));
  }

  @Test
  void updatePassenger_whenUpdateToOtherFields_thenReturnPassengerResponse() {
    String username = PassengerTestData.FIRST_USERNAME;
    Passenger passenger = PassengerTestData.getFirst().build();

    PassengerRequest request = PassengerTestData.getUpdateFirstRequest().build();
    Passenger updatedPassenger = PassengerTestData.getUpdatedFirst().build();
    PassengerResponse expected = PassengerTestData.getUpdatedFirstResponse().build();

    // Arrange
    when(passengerRepository.findByUsername(any(String.class))).thenReturn(Optional.of(passenger));
    when(passengerRepository.findByPhoneNumber(any(String.class))).thenReturn(Optional.empty());
    when(passengerRepository.save(any(Passenger.class))).thenReturn(updatedPassenger);

    // Act
    PassengerResponse actual = passengerService.updatePassenger(username, request);

    // Assert
    assertEquals(expected, actual);
    verify(passengerRepository).findByUsername(username);
    verify(passengerRepository).findByPhoneNumber(any(String.class));
    verify(passengerRepository).save(any(Passenger.class));
  }

  @Test
  void updatePassenger_whenSamePhoneNumberDifferentUser_thenThrowEntityAlreadyExistsException() {
    String username = PassengerTestData.FIRST_USERNAME;
    Passenger passenger = PassengerTestData.getFirst().build();
    PassengerRequest request = PassengerTestData.getUpdateFirstRequest().build();

    // Arrange
    when(passengerRepository.findByUsername(any(String.class))).thenReturn(Optional.of(passenger));
    when(passengerRepository.findByPhoneNumber(any(String.class)))
        .thenReturn(Optional.of(passenger));

    // Act & Assert
    assertThrows(
        EntityAlreadyExistsException.class,
        () -> passengerService.updatePassenger(username, request));
  }

  @Test
  void updatePassenger_whenNotExistingUsername_thenThrowEntityNotFoundException() {
    String username = PassengerTestData.FIRST_USERNAME;
    PassengerRequest request = PassengerTestData.getUpdateFirstRequest().build();

    // Arrange
    when(passengerRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        EntityNotFoundException.class, () -> passengerService.updatePassenger(username, request));
  }

  @Test
  void deletePassenger_whenPassengerWasFound_thenDeletePassenger() {
    String username = PassengerTestData.FIRST_USERNAME;
    Passenger passenger = PassengerTestData.getFirst().build();

    // Arrange
    when(passengerRepository.findByUsername(any(String.class))).thenReturn(Optional.of(passenger));

    // Act
    passengerService.deletePassenger(username);

    // Assert
    verify(passengerRepository).findByUsername(username);
    verify(passengerRepository).delete(any(Passenger.class));
  }

  @Test
  void deletePassenger_whenPassengerNotFound_thenDeletePassenger() {
    String username = PassengerTestData.FIRST_USERNAME;

    // Arrange
    when(passengerRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> passengerService.deletePassenger(username));
    verify(passengerRepository).findByUsername(username);
  }
}
