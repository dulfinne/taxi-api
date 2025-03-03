package com.dulfinne.taxi.passengerservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaginationTestData {

  public static final Integer DEFAULT_OFFSET = 0;
  public static final Integer DEFAULT_LIMIT = 10;
  public static final Integer DEFAULT_TOTAL_ELEMENTS = 10;
  public static final Integer DEFAULT_TOTAL_PAGES =
      (int)
          Math.ceil(
              (double) PaginationTestData.DEFAULT_TOTAL_ELEMENTS
                  / PaginationTestData.DEFAULT_LIMIT);

  public static final String PASSENGER_SORT_FIELD = "username";
  public static final String INVALID_SORT_FIELD = "invalidField";
  public static final String RATING_SORT_FIELD = "rating";
}
