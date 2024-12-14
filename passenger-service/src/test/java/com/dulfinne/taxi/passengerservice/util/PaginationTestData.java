package com.dulfinne.taxi.passengerservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PaginationTestData {

  public final Integer DEFAULT_OFFSET = 0;
  public final Integer DEFAULT_LIMIT = 10;
  public final String PASSENGER_SORT_FIELD = "username";
  public final String INVALID_SORT_FIELD = "invalidField";
  public final String RATING_SORT_FIELD = "rating";
}
