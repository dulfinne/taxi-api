package com.dulfinne.taxi.passengerservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionKeys {

  public static final String PASSENGER_NOT_FOUND_USERNAME = "passenger-not-found-username";

  public static final String PASSENGER_EXISTS_USERNAME = "passenger-exists-username";
  public static final String PASSENGER_EXISTS_PHONE_NUMBER = "passenger-exists-phone-number";
  public static final String ILLEGAL_SORT_FIELD = "illegal-sort-field";
  public static final String ACCESS_DENIED= "access-denied";
  public static final String UNKNOWN_ERROR = "unknown-error";
}
