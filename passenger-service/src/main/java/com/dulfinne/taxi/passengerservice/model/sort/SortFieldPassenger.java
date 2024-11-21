package com.dulfinne.taxi.passengerservice.model.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortFieldPassenger {
  ID("id"),
  USERNAME("username"),
  FIRST_NAME("firstName"),
  LAST_NAME("lastName"),
  PAYMENT("payment"),
  RIDE_COUNT("rideCount");

  private final String value;
}
