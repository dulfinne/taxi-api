package com.dulfinne.taxi.passengerservice.service.impl;

import static com.dulfinne.taxi.passengerservice.mapper.PassengerInfoMapper.INFO_MAPPER_INSTANCE;
import com.dulfinne.taxi.passengerservice.dto.request.PassengerInfoRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerInfoResponse;
import com.dulfinne.taxi.passengerservice.model.PassengerInfo;
import com.dulfinne.taxi.passengerservice.repository.PassengerInfoRepository;
import com.dulfinne.taxi.passengerservice.service.PassengerInfoService;
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
public class PassengerInfoServiceImpl implements PassengerInfoService {

  private final PassengerInfoRepository passengerInfoRepository;

  @Transactional(readOnly = true)
  @Override
  public Page<PassengerInfoResponse> getAllPassengersInfo(
      Integer offset, Integer limit, String sortField) {

    Page<PassengerInfo> passengersPage =
        passengerInfoRepository.findAll(
            PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortField)));

    return passengersPage.map(INFO_MAPPER_INSTANCE::toResponse);
  }

  @Transactional(readOnly = true)
  @Override
  public PassengerInfoResponse getPassengerInfoByPassengerId(Long passengerId) {
    PassengerInfo passengerInfo = getPassengerInfoIfExists(passengerId);
    return INFO_MAPPER_INSTANCE.toResponse(passengerInfo);
  }

  @Transactional
  @Override
  public PassengerInfoResponse savePassengerInfo(PassengerInfoRequest passengerInfoRequest) {
    PassengerInfo newPassengerInfo = INFO_MAPPER_INSTANCE.toEntity(passengerInfoRequest);

    checkPassengerIdUniqueness(newPassengerInfo.getPassengerId());
    checkPhoneNumberUniqueness(newPassengerInfo.getPhoneNumber());

    newPassengerInfo.setRideCount(0);
    newPassengerInfo.setAverageRating(5.0);

    passengerInfoRepository.save(newPassengerInfo);
    return INFO_MAPPER_INSTANCE.toResponse(newPassengerInfo);
  }

  @Transactional
  @Override
  public PassengerInfoResponse updatePassengerInfo(PassengerInfoRequest request) {
    PassengerInfo passengerInfo = getPassengerInfoIfExists(request.passengerId());

    checkPhoneNumberUniqueness(passengerInfo.getPhoneNumber(), request.phoneNumber());

    INFO_MAPPER_INSTANCE.updateEntity(request, passengerInfo);

    passengerInfoRepository.save(passengerInfo);
    return INFO_MAPPER_INSTANCE.toResponse(passengerInfo);
  }

  private void checkPhoneNumberUniqueness(String phoneNumber, String updatedPhoneNumber) {
    if (!updatedPhoneNumber.equals(phoneNumber)
        && passengerInfoRepository.findByPhoneNumber(updatedPhoneNumber).isPresent()) {
      throw new EntityExistsException(
          String.format("Passenger already exists: phoneNumber = %s", updatedPhoneNumber));
    }
  }

  private void checkPhoneNumberUniqueness(String phoneNumber) {
    if (passengerInfoRepository.findByPhoneNumber(phoneNumber).isPresent()) {
      throw new EntityExistsException(
          String.format("Passenger already exists: phoneNumber = %s", phoneNumber));
    }
  }

  private void checkPassengerIdUniqueness(Long passengerId) {
    if (passengerInfoRepository.findByPassengerId(passengerId).isPresent()) {
      throw new EntityExistsException(
          String.format("Passenger already exists: passengerId = %s", passengerId));
    }
  }

  private PassengerInfo getPassengerInfoIfExists(Long passengerId) {
    return passengerInfoRepository
        .findByPassengerId(passengerId)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format("Passenger not found: id = %d", passengerId)));
  }
}
