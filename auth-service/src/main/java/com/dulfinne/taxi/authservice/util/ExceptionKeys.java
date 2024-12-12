package com.dulfinne.taxi.authservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionKeys {
  public static final String KEYCLOAK_RESPONSE_ERROR = "keycloak-response-error";
  public static final String KEYCLOAK_UNAUTHORIZED = "keycloak-unauthorized";
  public static final String NOT_ALLOWED_CREATE_ROLE = "not-allowed-create-role";
  public static final String UNKNOWN_ERROR = "unknown-error";
}
