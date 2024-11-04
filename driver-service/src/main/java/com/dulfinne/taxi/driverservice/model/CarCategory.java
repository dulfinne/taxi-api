package com.dulfinne.taxi.driverservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CarCategory {
  ECONOM("Econom class"),
  COMFORT("Comfort class");

  private final String description;
}
