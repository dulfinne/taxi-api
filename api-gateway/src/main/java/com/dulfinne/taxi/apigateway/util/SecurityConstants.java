package com.dulfinne.taxi.apigateway.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityConstants {
  public static final String ROLES_CLAIM = "spring_sec_roles";
  public static final String USERNAME_HEADER = "X-Username";
  public static final String BEARER = "Bearer ";
  public static final int BEARER_LENGTH = 7;
}