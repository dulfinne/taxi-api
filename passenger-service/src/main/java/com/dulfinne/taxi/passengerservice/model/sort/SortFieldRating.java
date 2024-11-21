package com.dulfinne.taxi.passengerservice.model.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortFieldRating {
  ID("id"),
  RATING("rating");

  private final String value;
}
