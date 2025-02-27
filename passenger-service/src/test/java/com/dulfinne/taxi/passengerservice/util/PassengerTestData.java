package com.dulfinne.taxi.passengerservice.util;

import com.dulfinne.jooq.generated.public_.tables.records.PassengerRecord;
import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import com.dulfinne.taxi.passengerservice.model.Payment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PassengerTestData {

  public static final String NON_EXISTING_PASSENGER_USERNAME = "notfound123";
  public static final String ADMIN_USERNAME = "admin";
  public static final String EXISTING_PHONE_NUMBER = "375443333333";

  public static final String FIRST_USERNAME = "anna123";
  public static final Long FIRST_ID = 1L;
  public static final String FIRST_FIRSTNAME = "anna";
  public static final String FIRST_LASTNAME = "victorova";
  public static final String FIRST_PHONE_NUMBER = "375441111111";
  public static final Payment FIRST_PAYMENT = Payment.CASH;
  public static final Integer FIRST_RIDE_COUNT = 10;
  public static final BigDecimal FIRST_SUM_OF_RATINGS = BigDecimal.valueOf(10.0);
  public static final Integer FIRST_NUMBER_OF_RATINGS = 5;
  public static final BigDecimal FIRST_AVERAGE_RATING =
      FIRST_SUM_OF_RATINGS.divide(
          BigDecimal.valueOf(FIRST_NUMBER_OF_RATINGS), 2, RoundingMode.HALF_UP);

  public static final Long SECOND_ID = 3L;
  public static final String SECOND_USERNAME = "zhanna123";
  public static final String SECOND_FIRSTNAME = "zhanna";
  public static final String SECOND_LASTNAME = "dictorova";
  public static final String SECOND_PHONE_NUMBER = "375442222222";
  public static final Payment SECOND_PAYMENT = Payment.CARD;
  public static final Integer SECOND_RIDE_COUNT = 5;
  public static final BigDecimal SECOND_SUM_OF_RATINGS = BigDecimal.valueOf(5.0);
  public static final Integer SECOND_NUMBER_OF_RATINGS = 1;
  public static final BigDecimal SECOND_AVERAGE_RATING =
      SECOND_SUM_OF_RATINGS.divide(
          BigDecimal.valueOf(SECOND_NUMBER_OF_RATINGS), 2, RoundingMode.HALF_UP);

  public static final BigDecimal START_AVERAGE_RATING =
      PassengerConstants.START_SUM_OF_RATINGS.divide(
          BigDecimal.valueOf(PassengerConstants.START_NUMBER_OF_RATINGS), 2, RoundingMode.HALF_UP);

  public static PassengerRequest.PassengerRequestBuilder getFirstRequest() {
    return PassengerRequest.builder()
        .firstName(FIRST_FIRSTNAME)
        .lastName(FIRST_LASTNAME)
        .phoneNumber(FIRST_PHONE_NUMBER)
        .payment(FIRST_PAYMENT);
  }

  public static PassengerRequest.PassengerRequestBuilder getSecondCreateRequest() {
    return PassengerRequest.builder()
        .firstName(SECOND_FIRSTNAME)
        .lastName(SECOND_LASTNAME)
        .phoneNumber(SECOND_PHONE_NUMBER)
        .payment(SECOND_PAYMENT);
  }

  public static PassengerRequest.PassengerRequestBuilder getUpdateFirstRequest() {
    return PassengerRequest.builder()
        .firstName(SECOND_FIRSTNAME)
        .lastName(SECOND_LASTNAME)
        .phoneNumber(SECOND_PHONE_NUMBER)
        .payment(SECOND_PAYMENT);
  }

  public static List<PassengerRecord> getPassengerList() {
    return List.of(getFirst(), getSecond());
  }

  public static PassengerRecord getFirst() {
    return new PassengerRecord(
        FIRST_ID,
        FIRST_FIRSTNAME,
        FIRST_LASTNAME,
        FIRST_PHONE_NUMBER,
        FIRST_PAYMENT.toString(),
        FIRST_RIDE_COUNT,
        FIRST_USERNAME,
        FIRST_SUM_OF_RATINGS,
        FIRST_NUMBER_OF_RATINGS);
  }

  public static PassengerRecord getSecond() {
    return new PassengerRecord(
        SECOND_ID,
        SECOND_FIRSTNAME,
        SECOND_LASTNAME,
        SECOND_PHONE_NUMBER,
        SECOND_PAYMENT.toString(),
        SECOND_RIDE_COUNT,
        SECOND_USERNAME,
        SECOND_SUM_OF_RATINGS,
        SECOND_NUMBER_OF_RATINGS);
  }

  public static PassengerRecord getUpdatedFirst() {
    return new PassengerRecord(
        FIRST_ID,
        SECOND_FIRSTNAME,
        SECOND_LASTNAME,
        SECOND_PHONE_NUMBER,
        SECOND_PAYMENT.toString(),
        FIRST_RIDE_COUNT,
        FIRST_USERNAME,
        FIRST_SUM_OF_RATINGS,
        FIRST_NUMBER_OF_RATINGS);
  }

  public static List<PassengerResponse> getResponseList() {
    return List.of(getFirstResponse().build(), getSecondResponse().build());
  }

  public static PassengerResponse.PassengerResponseBuilder getFirstResponse() {
    return PassengerResponse.builder()
        .id(FIRST_ID)
        .username(FIRST_USERNAME)
        .firstName(FIRST_FIRSTNAME)
        .lastName(FIRST_LASTNAME)
        .phoneNumber(FIRST_PHONE_NUMBER)
        .payment(FIRST_PAYMENT)
        .rideCount(FIRST_RIDE_COUNT)
        .averageRating(FIRST_AVERAGE_RATING.doubleValue());
  }

  public static PassengerResponse.PassengerResponseBuilder getSecondResponse() {
    return PassengerResponse.builder()
        .id(SECOND_ID)
        .username(SECOND_USERNAME)
        .firstName(SECOND_FIRSTNAME)
        .lastName(SECOND_LASTNAME)
        .phoneNumber(SECOND_PHONE_NUMBER)
        .payment(SECOND_PAYMENT)
        .rideCount(SECOND_RIDE_COUNT)
        .averageRating(SECOND_AVERAGE_RATING.doubleValue());
  }

  public static PassengerResponse.PassengerResponseBuilder getSecondCreatedResponse() {
    return PassengerResponse.builder()
        .id(SECOND_ID)
        .username(SECOND_USERNAME)
        .firstName(SECOND_FIRSTNAME)
        .lastName(SECOND_LASTNAME)
        .phoneNumber(SECOND_PHONE_NUMBER)
        .payment(SECOND_PAYMENT)
        .rideCount(PassengerConstants.START_RIDE_COUNT)
        .averageRating(START_AVERAGE_RATING.doubleValue());
  }

  public static PassengerResponse.PassengerResponseBuilder getUpdatedFirstResponse() {
    return PassengerResponse.builder()
        .id(FIRST_ID)
        .username(FIRST_USERNAME)
        .firstName(SECOND_FIRSTNAME)
        .lastName(SECOND_LASTNAME)
        .phoneNumber(SECOND_PHONE_NUMBER)
        .payment(SECOND_PAYMENT)
        .rideCount(FIRST_RIDE_COUNT)
        .averageRating(FIRST_AVERAGE_RATING.doubleValue());
  }
}
