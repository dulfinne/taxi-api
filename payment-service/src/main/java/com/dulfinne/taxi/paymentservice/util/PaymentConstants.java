package com.dulfinne.taxi.paymentservice.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class PaymentConstants {
  public static final BigDecimal DRIVER_PAYOUT_RATE = BigDecimal.valueOf(0.7);
  public static final BigDecimal MIN_AMOUNT_LIMIT = BigDecimal.valueOf(2.6);
  public static final BigDecimal MAX_AMOUNT_LIMIT = BigDecimal.valueOf(500);
}
