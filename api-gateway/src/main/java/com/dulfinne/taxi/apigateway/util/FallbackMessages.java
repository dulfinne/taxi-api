package com.dulfinne.taxi.apigateway.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FallbackMessages {
  public static final String PASSENGER_SERVICE_DOWN =
      "Passenger service is unavailable. Please try again later.";
  public static final String DRIVER_SERVICE_DOWN =
      "Driver service is unavailable. Please try again later.";
  public static final String RIDE_SERVICE_DOWN =
      "Ride service is unavailable. Please try again later.";
  public static final String AUTH_SERVICE_DOWN =
      "Auth service is unavailable. Please try again later.";
  public static final String PAYMENT_SERVICE_DOWN =
      "Payment service is unavailable. Please try again later.";
  public static final String PROMOCODE_SERVICE_DOWN =
      "Promocode service is unavailable. Please try again later.";
}
