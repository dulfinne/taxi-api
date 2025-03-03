package com.dulfinne.taxi.passengerservice.service.impl;

import com.dulfinne.jooq.generated.tables.records.PassengerRecord;
import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import com.dulfinne.taxi.passengerservice.exception.EntityAlreadyExistsException;
import com.dulfinne.taxi.passengerservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.passengerservice.exception.IllegalSortFieldException;
import com.dulfinne.taxi.passengerservice.mapper.PassengerMapper;
import com.dulfinne.taxi.passengerservice.model.sort.SortFieldPassenger;
import com.dulfinne.taxi.passengerservice.repository.PassengerRepository;
import com.dulfinne.taxi.passengerservice.service.PassengerService;
import com.dulfinne.taxi.passengerservice.util.ExceptionKeys;
import com.dulfinne.taxi.passengerservice.util.PassengerConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PassengerServiceImpl implements PassengerService {

  private final PassengerRepository passengerRepository;
  private final PassengerMapper passengerMapper;

  @Transactional(readOnly = true)
  @Override
  public List<PassengerResponse> getAllPassengers(Integer offset, Integer limit, String sortField) {
    log.info("Getting all passengers. Started. Sort field = {}", sortField);
    checkSortFieldIsValid(sortField);

    return passengerRepository.findAll(offset, limit, sortField).stream()
        .map(passengerMapper::toResponse)
        .toList();
  }

  @Transactional(readOnly = true)
  @Override
  public PassengerResponse getPassengerByUsername(String username) {
    log.info("Getting passenger by username. Started. Username = {}", username);
    PassengerRecord passenger = getPassengerIfExistsByUsername(username);
    return passengerMapper.toResponse(passenger);
  }

  @Transactional
  @Override
  public PassengerResponse savePassenger(String username, PassengerRequest request) {
    log.info("Saving passenger. Started. Username = {}", username);
    checkUsernameUniqueness(username);
    checkPhoneNumberUniqueness(request.phoneNumber());

    PassengerRecord passenger = passengerMapper.toEntity(request);
    passenger.setUsername(username);
    passenger.setRideCount(PassengerConstants.START_RIDE_COUNT);
    passenger.setSumOfRatings(PassengerConstants.START_SUM_OF_RATINGS);
    passenger.setNumberOfRatings(PassengerConstants.START_NUMBER_OF_RATINGS);
    return passengerMapper.toResponse(passengerRepository.save(passenger));
  }

  @Transactional
  @Override
  public PassengerResponse updatePassenger(String username, PassengerRequest request) {
    log.info("Updating passenger. Started. Username = {}", username);
    PassengerRecord passenger = getPassengerIfExistsByUsername(username);
    checkPhoneNumberUniqueness(passenger.getPhoneNumber(), request.phoneNumber());

    passengerMapper.updateEntity(request, passenger);
    passenger = passengerRepository.update(username, passenger);
    return passengerMapper.toResponse(passenger);
  }

  @Transactional
  @Override
  public void deletePassenger(String username) {
    log.info("Deleting passenger. Started. Username = {}", username);
    getPassengerIfExistsByUsername(username);
    passengerRepository.delete(username);
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

  private PassengerRecord getPassengerIfExistsByUsername(String username) {
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
