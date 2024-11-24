package com.dulfinne.taxi.driverservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionKeys {

  public static final String DRIVER_NOT_FOUND_USERNAME = "driver-not-found-username";
  public static final String CAR_NOT_FOUND_ID = "car-not-found-id";

  public static final String DRIVER_EXISTS_USERNAME = "driver-exists-username";
  public static final String DRIVER_EXISTS_PHONE_NUMBER = "driver-exists-phone-number";
  public static final String CAR_EXISTS_REGISTRATION_NUMBER = "car-exists-registration-number";
  public static final String CAR_ALREADY_ASSIGNED = "car-already-assigned";
  public static final String ACCESS_DENIED = "access-denied";
  public static final String UNKNOWN_ERROR = "unknown-error";
}
