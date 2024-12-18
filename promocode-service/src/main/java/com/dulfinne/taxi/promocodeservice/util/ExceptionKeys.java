package com.dulfinne.taxi.promocodeservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionKeys {

  public static final String PROMOCODE_NOT_FOUND_CODE = "promocode-not-found-code";
  public static final String USAGE_NOT_FOUND_ID = "usage-not-found-id";
  public static final String PROMOCODE_EXISTS_CODE = "promocode-exists-code";
  public static final String PROMOCODE_NOT_ACTIVE = "promocode-not-active";
  public static final String USAGE_COUNT_EXCEED_MAX = "usage-count-exceed-max";
  public static final String PROMOCODE_ALREADY_USED = "promocode-already-used";
  public static final String UNKNOWN_ERROR = "unknown-error";
}
