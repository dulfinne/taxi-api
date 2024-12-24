package com.dulfinne.taxi.passengerservice.util;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import com.dulfinne.taxi.passengerservice.model.Passenger;
import com.dulfinne.taxi.passengerservice.model.Payment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PassengerTestData {

  public static final String FIRST_USERNAME = "anna123";
  public static final Long FIRST_ID = 1L;
  public static final String FIRST_FIRSTNAME = "anna";
  public static final String FIRST_LASTNAME = "victorova";
  public static final String FIRST_PHONE_NUMBER = "375441111111";
  public static final Payment FIRST_PAYMENT = Payment.CASH;
  public static final Integer FIRST_RIDE_COUNT = 10;
  public static final Double FIRST_SUM_OF_RATINGS = 10.0;
  public static final Integer FIRST_NUMBER_OF_RATINGS = 5;
  public static final Double FIRST_AVERAGE_RATING = FIRST_SUM_OF_RATINGS / FIRST_NUMBER_OF_RATINGS;

  public static final Long SECOND_ID = 2L;
  public static final String SECOND_USERNAME = "zhanna123";
  public static final String SECOND_FIRSTNAME = "zhanna";
  public static final String SECOND_LASTNAME = "dictorova";
  public static final String SECOND_PHONE_NUMBER = "375442222222";
  public static final Payment SECOND_PAYMENT = Payment.CARD;
  public static final Integer SECOND_RIDE_COUNT = 5;
  public static final Double SECOND_SUM_OF_RATINGS = 5.0;
  public static final Integer SECOND_NUMBER_OF_RATINGS = 1;
  public static final Double SECOND_AVERAGE_RATING =
      SECOND_SUM_OF_RATINGS / SECOND_NUMBER_OF_RATINGS;

  public static final Double START_AVERAGE_RATING =
      PassengerConstants.START_SUM_OF_RATINGS / PassengerConstants.START_NUMBER_OF_RATINGS;

  public static PassengerRequest.PassengerRequestBuilder getFirstRequest() {
    return PassengerRequest.builder()
        .firstName(FIRST_FIRSTNAME)
        .lastName(FIRST_LASTNAME)
        .phoneNumber(FIRST_PHONE_NUMBER)
        .payment(FIRST_PAYMENT);
  }

  public static PassengerRequest.PassengerRequestBuilder getUpdateFirstRequest() {
    return PassengerRequest.builder()
        .firstName(SECOND_FIRSTNAME)
        .lastName(SECOND_LASTNAME)
        .phoneNumber(SECOND_PHONE_NUMBER)
        .payment(SECOND_PAYMENT);
  }

  public static List<Passenger> getPassengerList() {
    return List.of(getFirst().build(), getSecond().build());
  }

  public static Passenger.PassengerBuilder getFirst() {
    return Passenger.builder()
        .id(FIRST_ID)
        .username(FIRST_USERNAME)
        .firstName(FIRST_FIRSTNAME)
        .lastName(FIRST_LASTNAME)
        .phoneNumber(FIRST_PHONE_NUMBER)
        .payment(FIRST_PAYMENT)
        .rideCount(FIRST_RIDE_COUNT)
        .numberOfRatings(FIRST_NUMBER_OF_RATINGS)
        .sumOfRatings(FIRST_SUM_OF_RATINGS);
  }

  public static Passenger.PassengerBuilder getSecond() {
    return Passenger.builder()
        .id(SECOND_ID)
        .username(SECOND_USERNAME)
        .firstName(SECOND_FIRSTNAME)
        .lastName(SECOND_LASTNAME)
        .phoneNumber(SECOND_PHONE_NUMBER)
        .payment(SECOND_PAYMENT)
        .rideCount(SECOND_RIDE_COUNT)
        .numberOfRatings(SECOND_NUMBER_OF_RATINGS)
        .sumOfRatings(SECOND_SUM_OF_RATINGS);
  }

  public static Passenger.PassengerBuilder getUpdatedFirst() {
    return Passenger.builder()
        .id(FIRST_ID)
        .username(FIRST_USERNAME)
        .firstName(SECOND_FIRSTNAME)
        .lastName(SECOND_LASTNAME)
        .phoneNumber(SECOND_PHONE_NUMBER)
        .payment(SECOND_PAYMENT)
        .rideCount(FIRST_RIDE_COUNT)
        .numberOfRatings(FIRST_NUMBER_OF_RATINGS)
        .sumOfRatings(FIRST_SUM_OF_RATINGS);
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
        .averageRating(FIRST_AVERAGE_RATING);
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
        .averageRating(SECOND_AVERAGE_RATING);
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
        .averageRating(FIRST_AVERAGE_RATING);
  }
}
