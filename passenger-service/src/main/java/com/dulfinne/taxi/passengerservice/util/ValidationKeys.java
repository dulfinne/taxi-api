package com.dulfinne.taxi.passengerservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationKeys {

  public static final String RATING_REQUIRED = "rating-required";
  public static final String FIRST_NAME_REQUIRED = "first-name-required";
  public static final String LAST_NAME_REQUIRED = "last-name-required";
  public static final String PHONE_NUMBER_REQUIRED = "phone-number-required";
  public static final String PAYMENT_REQUIRED = "payment-required";

  public static final String INVALID_FEEDBACK_LENGTH = "invalid-feedback-length";
  public static final String INVALID_PHONE_NUMBER = "invalid-phone-number";

  public static final String PHONE_NUMBER_FORMAT = "^375(33|44|29|25)\\d{7}$";
}
