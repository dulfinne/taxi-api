package com.dulfinne.taxi.passengerservice.service.impl;

import static com.dulfinne.taxi.passengerservice.mapper.PassengerMapper.INFO_MAPPER_INSTANCE;
import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import com.dulfinne.taxi.passengerservice.exception.EntityAlreadyExistsException;
import com.dulfinne.taxi.passengerservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.passengerservice.exception.IllegalSortFieldException;
import com.dulfinne.taxi.passengerservice.model.Passenger;
import com.dulfinne.taxi.passengerservice.model.sort.SortFieldPassenger;
import com.dulfinne.taxi.passengerservice.repository.PassengerRepository;
import com.dulfinne.taxi.passengerservice.service.PassengerService;
import com.dulfinne.taxi.passengerservice.util.ExceptionKeys;
import com.dulfinne.taxi.passengerservice.util.PassengerConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

  private final PassengerRepository passengerRepository;

  @Transactional(readOnly = true)
  @Override
  public Page<PassengerResponse> getAllPassengers(Integer offset, Integer limit, String sortField) {
    checkSortFieldIsValid(sortField);

    Page<Passenger> passengersPage =
        passengerRepository.findAll(
            PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortField)));

    return passengersPage.map(INFO_MAPPER_INSTANCE::toResponse);
  }

  @Transactional(readOnly = true)
  @Override
  public PassengerResponse getPassengerByUsername(String username) {
    Passenger passenger = getPassengerIfExistsByUsername(username);
    return INFO_MAPPER_INSTANCE.toResponse(passenger);
  }

  @Transactional
  @Override
  public PassengerResponse savePassenger(String username, PassengerRequest request) {
    checkUsernameUniqueness(username);
    checkPhoneNumberUniqueness(request.phoneNumber());

    Passenger passenger = INFO_MAPPER_INSTANCE.toEntity(request);
    passenger.setUsername(username);
    passenger.setRideCount(PassengerConstants.START_RIDE_COUNT);
    passenger.setSumOfRatings(PassengerConstants.START_SUM_OF_RATINGS);
    passenger.setNumberOfRatings(PassengerConstants.START_NUMBER_OF_RATINGS);
    passenger = passengerRepository.save(passenger);
    return INFO_MAPPER_INSTANCE.toResponse(passenger);
  }

  @Transactional
  @Override
  public PassengerResponse updatePassenger(String username, PassengerRequest request) {
    Passenger passenger = getPassengerIfExistsByUsername(username);
    checkPhoneNumberUniqueness(passenger.getPhoneNumber(), request.phoneNumber());

    INFO_MAPPER_INSTANCE.updateEntity(request, passenger);
    passenger = passengerRepository.save(passenger);
    return INFO_MAPPER_INSTANCE.toResponse(passenger);
  }

  @Transactional
  @Override
  public void deletePassenger(String username) {
    Passenger passenger = getPassengerIfExistsByUsername(username);
    passengerRepository.delete(passenger);
  }

  private void checkPhoneNumberUniqueness(String phoneNumber, String updatedPhoneNumber) {
    if (!updatedPhoneNumber.equals(phoneNumber)
        && passengerRepository.findByPhoneNumber(updatedPhoneNumber).isPresent()) {
      throw new EntityAlreadyExistsException(
          ExceptionKeys.PASSENGER_EXISTS_PHONE_NUMBER, updatedPhoneNumber);
    }
  }

  private void checkPhoneNumberUniqueness(String phoneNumber) {
    if (passengerRepository.findByPhoneNumber(phoneNumber).isPresent()) {
      throw new EntityAlreadyExistsException(
          ExceptionKeys.PASSENGER_EXISTS_PHONE_NUMBER, phoneNumber);
    }
  }

  private Passenger getPassengerIfExistsByUsername(String username) {
    return passengerRepository
        .findByUsername(username)
        .orElseThrow(
            () ->
                new EntityNotFoundException(ExceptionKeys.PASSENGER_NOT_FOUND_USERNAME, username));
  }

  private void checkUsernameUniqueness(String username) {
    if (passengerRepository.findByUsername(username).isPresent()) {
      throw new EntityAlreadyExistsException(ExceptionKeys.PASSENGER_EXISTS_USERNAME, username);
    }
  }

  private void checkSortFieldIsValid(String sortField) {
    boolean isValid =
        Arrays.stream(SortFieldPassenger.values())
            .anyMatch(field -> field.getValue().equals(sortField));

    if (!isValid) {
      throw new IllegalSortFieldException(ExceptionKeys.ILLEGAL_SORT_FIELD, sortField);
    }
  }
}
