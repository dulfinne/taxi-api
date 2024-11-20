package com.dulfinne.taxi.passengerservice.service.impl;

import static com.dulfinne.taxi.passengerservice.mapper.PassengerMapper.INFO_MAPPER_INSTANCE;
import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import com.dulfinne.taxi.passengerservice.model.Passenger;
import com.dulfinne.taxi.passengerservice.repository.PassengerRepository;
import com.dulfinne.taxi.passengerservice.service.PassengerService;
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
public class PassengerServiceImpl implements PassengerService {

  private final PassengerRepository passengerRepository;

  @Transactional(readOnly = true)
  @Override
  public Page<PassengerResponse> getAllPassengers(Integer offset, Integer limit, String sortField) {

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
    passenger.setRideCount(0);
    passenger.setSumOfRatings(5.0);
    passenger.setNumberOfRatings(1);
    passengerRepository.save(passenger);
    return INFO_MAPPER_INSTANCE.toResponse(passenger);
  }

  @Transactional
  @Override
  public PassengerResponse updatePassenger(String username, PassengerRequest request) {
    Passenger passenger = getPassengerIfExistsByUsername(username);
    checkPhoneNumberUniqueness(passenger.getPhoneNumber(), request.phoneNumber());

    INFO_MAPPER_INSTANCE.updateEntity(request, passenger);
    passengerRepository.save(passenger);
    return INFO_MAPPER_INSTANCE.toResponse(passenger);
  }

  @Transactional
  @Override
  public void deletePassenger(String username) {
    Passenger driver = getPassengerIfExistsByUsername(username);
    passengerRepository.delete(driver);
  }

  private void checkPhoneNumberUniqueness(String phoneNumber, String updatedPhoneNumber) {
    if (!updatedPhoneNumber.equals(phoneNumber)
        && passengerRepository.findByPhoneNumber(updatedPhoneNumber).isPresent()) {
      throw new EntityExistsException(
          String.format("Passenger already exists: phoneNumber = %s", updatedPhoneNumber));
    }
  }

  private void checkPhoneNumberUniqueness(String phoneNumber) {
    if (passengerRepository.findByPhoneNumber(phoneNumber).isPresent()) {
      throw new EntityExistsException(
          String.format("Passenger already exists: phoneNumber = %s", phoneNumber));
    }
  }

  private Passenger getPassengerIfExistsByUsername(String username) {
    return passengerRepository
        .findByUsername(username)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format("Passenger not found: username = %s", username)));
  }

  private void checkUsernameUniqueness(String username) {
    if (passengerRepository.findByUsername(username).isPresent()) {
      throw new EntityExistsException(
          String.format("Passenger already exists: username = %s", username));
    }
  }
}
