package com.dulfinne.taxi.driverservice.service.impl;

import com.dulfinne.taxi.driverservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.driverservice.exception.EntityAlreadyExistsException;
import com.dulfinne.taxi.driverservice.dto.request.DriverRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverResponse;
import com.dulfinne.taxi.driverservice.mapper.DriverMapper;
import com.dulfinne.taxi.driverservice.model.Car;
import com.dulfinne.taxi.driverservice.model.Driver;
import com.dulfinne.taxi.driverservice.repository.CarRepository;
import com.dulfinne.taxi.driverservice.repository.DriverRepository;
import com.dulfinne.taxi.driverservice.service.DriverService;
import com.dulfinne.taxi.driverservice.util.ExceptionKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

  private final DriverRepository driverRepository;
  private final CarRepository carRepository;
  private final DriverMapper driverMapper;

  @Transactional(readOnly = true)
  @Override
  public Page<DriverResponse> getAllDrivers(Integer offset, Integer limit, String sortField) {
    Page<Driver> driversPage =
        driverRepository.findAll(
            PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortField)));

    return driversPage.map(driverMapper::toResponse);
  }

  @Transactional(readOnly = true)
  @Override
  public DriverResponse getDriverById(Long id) {
    Driver driver = getDriverIfExist(id);

    return driverMapper.toResponse(driver);
  }

  @Transactional
  @Override
  public DriverResponse saveDriver(String username, DriverRequest driverRequest) {
    checkUsernameUniqueness(username);
    checkPhoneNumberUniqueness(driverRequest.phoneNumber());

    Driver driver = driverMapper.toEntity(driverRequest);
    driver.setUsername(username);
    driver.setSumOfRatings(0.0);
    driver.setNumberOfRatings(0);
    driverRepository.save(driver);
    return driverMapper.toResponse(driver);
  }

  @Transactional
  @Override
  public DriverResponse updateDriver(Long id, DriverRequest driverRequest) {
    Driver driver = getDriverIfExist(id);
    checkPhoneNumberUniqueness(driver.getPhoneNumber(), driverRequest.phoneNumber());

    driverMapper.updateEntity(driverRequest, driver);
    driverRepository.save(driver);
    return driverMapper.toResponse(driver);
  }

  @Transactional
  @Override
  public void deleteDriver(Long id) {
    Driver driver = getDriverIfExist(id);
    driverRepository.delete(driver);
  }

  @Transactional
  @Override
  public DriverResponse assignCarToDriver(Long driverId, Long carId) {
    Driver driver = getDriverIfExist(driverId);
    Car car = getCarIfExist(carId);
    checkCarIdUniqueness(driverId, carId);

    driver.setCar(car);
    driverRepository.save(driver);

    return driverMapper.toResponse(driver);
  }

  @Transactional
  @Override
  public DriverResponse removeCarFromDriver(Long driverId, Long carId) {
    Driver driver = getDriverIfExist(driverId);
    Car car = getCarIfExist(carId);

    driver.setCar(null);
    driverRepository.save(driver);

    return driverMapper.toResponse(driver);
  }

  private Driver getDriverIfExist(Long id) {
    return driverRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(ExceptionKeys.DRIVER_NOT_FOUND_ID, id));
  }

  private Car getCarIfExist(Long id) {
    return carRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(ExceptionKeys.CAR_NOT_FOUND_ID, id));
  }

  private void checkCarIdUniqueness(Long driverId, Long carId) {
    if (driverRepository.findByCarId(carId).isPresent()) {
      throw new EntityAlreadyExistsException(ExceptionKeys.CAR_ALREADY_ASSIGNED, driverId, carId);
    }
  }

  private void checkUsernameUniqueness(String username) {
    if (driverRepository.findByUsername(username).isPresent()) {
      throw new EntityAlreadyExistsException(ExceptionKeys.DRIVER_EXISTS_USERNAME, username);
    }
  }

  private void checkPhoneNumberUniqueness(String phoneNumber) {
    if (driverRepository.findByPhoneNumber(phoneNumber).isPresent()) {
      throw new EntityAlreadyExistsException(ExceptionKeys.DRIVER_EXISTS_PHONE_NUMBER, phoneNumber);
    }
  }

  private void checkPhoneNumberUniqueness(String phoneNumber, String updatedPhoneNumber) {
    if (!updatedPhoneNumber.equals(phoneNumber)
        && driverRepository.findByPhoneNumber(updatedPhoneNumber).isPresent()) {
      throw new EntityAlreadyExistsException(
          ExceptionKeys.DRIVER_EXISTS_PHONE_NUMBER, updatedPhoneNumber);
    }
  }
}
