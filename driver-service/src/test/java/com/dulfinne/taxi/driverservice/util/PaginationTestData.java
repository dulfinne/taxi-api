package com.dulfinne.taxi.driverservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaginationTestData {

  public static final Integer DEFAULT_OFFSET = 0;
  public static final Integer DEFAULT_LIMIT = 10;
  public static final String DRIVER_SORT_FIELD = "username";
  public static final String CAR_SORT_FIELD = "carCategory";
  public static final String RATING_SORT_FIELD = "rating";

}
