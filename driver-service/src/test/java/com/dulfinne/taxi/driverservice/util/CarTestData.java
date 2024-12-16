package com.dulfinne.taxi.driverservice.util;

import com.dulfinne.taxi.driverservice.dto.request.CarRequest;
import com.dulfinne.taxi.driverservice.dto.response.CarResponse;
import com.dulfinne.taxi.driverservice.model.Car;
import com.dulfinne.taxi.driverservice.model.CarCategory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CarTestData {

  public static final Long ID = 1L;
  public static final String NAME = "BMW";
  public static final String REGISTRATION_NUMBER = "1234AA-5";
  public static final CarCategory CAR_CATEGORY = CarCategory.ECONOM;
  public static final String COLOR = "Black";

  public static final String UPDATED_NAME = "LADA";
  public static final String UPDATED_REGISTRATION_NUMBER = "1234AA-6";
  public static final CarCategory UPDATED_CAR_CATEGORY = CarCategory.COMFORT;
  public static final String UPDATED_COLOR = "Velvet";

  public Car getCar() {
    return Car.builder()
            .id(ID)
            .name(NAME)
            .registrationNumber(REGISTRATION_NUMBER)
            .carCategory(CAR_CATEGORY)
            .color(COLOR)
            .build();
  }

  public CarRequest getRequest() {
    return CarRequest.builder()
            .name(NAME)
            .registrationNumber(REGISTRATION_NUMBER)
            .carCategory(CAR_CATEGORY)
            .color(COLOR)
            .build();
  }

  public CarRequest getUpdateRequest() {
    return CarRequest.builder()
            .name(UPDATED_NAME)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .carCategory(UPDATED_CAR_CATEGORY)
            .color(UPDATED_COLOR)
            .build();
  }

  public CarResponse getResponse() {
    return CarResponse.builder()
            .id(ID)
            .name(NAME)
            .registrationNumber(REGISTRATION_NUMBER)
            .carCategory(CAR_CATEGORY)
            .color(COLOR)
            .build();
  }

  public CarResponse getUpdateResponse() {
    return CarResponse.builder()
            .id(ID)
            .name(UPDATED_NAME)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .carCategory(UPDATED_CAR_CATEGORY)
            .color(UPDATED_COLOR)
            .build();
  }
}
