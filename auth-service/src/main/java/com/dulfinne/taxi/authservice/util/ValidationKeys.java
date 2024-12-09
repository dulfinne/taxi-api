package com.dulfinne.taxi.authservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationKeys {
  public static final String USERNAME_REQUIRED = "username-required";
  public static final String PASSWORD_REQUIRED = "password-required";
  public static final String EMAIL_REQUIRED = "email-required";
  public static final String ROLE_REQUIRED = "role-required";

  public static final String INVALID_USERNAME = "invalid-username";
  public static final String INVALID_PASSWORD = "invalid-password";
  public static final String INVALID_EMAIL = "invalid-email";

  public static final String USERNAME_FORMAT = "^(?=.+[a-z])[a-z\\d_]{5,20}$";
  public static final String PASSWORD_FORMAT = "^(?=.+\\d)[A-Za-z\\d]{8,20}$";
}
