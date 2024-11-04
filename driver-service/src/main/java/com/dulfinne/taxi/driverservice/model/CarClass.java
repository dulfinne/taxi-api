package com.dulfinne.taxi.driverservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CarClass {
  ECONOM("Econom class"),
  COMFORT("Comfort class");

  private final String description;
}
