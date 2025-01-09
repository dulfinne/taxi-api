package com.dulfinne.taxi.paymentservice.util;

import com.dulfinne.taxi.avro.PaymentRequest;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class PaymentTestData {

  public static final String PASSENGER_USERNAME = "anna123";
  public static final String DRIVER_USERNAME = "zhanna123";
  public static final BigDecimal PAYMENT_AMOUNT = BigDecimal.valueOf(77);
  public static final Long RIDE_ID = 1L;

  public static PaymentRequest getPaymentRequest() {
    return PaymentRequest.newBuilder()
            .setRideId(RIDE_ID)
            .setPassengerUsername(PASSENGER_USERNAME)
            .setDriverUsername(DRIVER_USERNAME)
            .setPrice(PAYMENT_AMOUNT.toString())
            .build();
  }
}
