package com.dulfinne.taxi.passengerservice.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class PassengerConstants {
    public final int START_RIDE_COUNT = 0;
    public final BigDecimal START_SUM_OF_RATINGS = BigDecimal.valueOf(5.0);
    public final int START_NUMBER_OF_RATINGS = 1;
}
