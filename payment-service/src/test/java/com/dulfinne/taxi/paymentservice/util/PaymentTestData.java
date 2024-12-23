package com.dulfinne.taxi.paymentservice.util;

import com.dulfinne.taxi.paymentservice.dto.request.PaymentRequest;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class PaymentTestData {

  public static final String PASSENGER_USERNAME = "anna123";
  public static final String DRIVER_USERNAME = "zhanna123";
  public static final BigDecimal PAYMENT_AMOUNT = BigDecimal.valueOf(77);

  public static PaymentRequest getPaymentRequest() {
    return PaymentRequest.builder()
            .passengerUsername(PASSENGER_USERNAME)
            .driverUsername(DRIVER_USERNAME)
            .price(PAYMENT_AMOUNT)
            .build();
  }
}
