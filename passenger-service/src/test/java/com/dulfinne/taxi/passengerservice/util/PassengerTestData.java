package com.dulfinne.taxi.passengerservice.util;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import com.dulfinne.taxi.passengerservice.model.Passenger;
import com.dulfinne.taxi.passengerservice.model.Payment;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class PassengerTestData {

  public final Long FIRST_ID = 1L;
  public final String FIRST_USERNAME = "anna123";
  public final String FIRST_FIRSTNAME = "anna";
  public final String FIRST_LASTNAME = "victorova";
  public final String FIRST_PHONE_NUMBER = "375441111111";
  public final Payment FIRST_PAYMENT = Payment.CASH;
  public final Integer FIRST_RIDE_COUNT = 10;
  public final Double FIRST_SUM_OF_RATINGS = 10.0;
  public final Integer FIRST_NUMBER_OF_RATINGS = 5;
  public final Double FIRST_AVERAGE_RATING = FIRST_SUM_OF_RATINGS / FIRST_NUMBER_OF_RATINGS;

  public final Long SECOND_ID = 2L;
  public final String SECOND_USERNAME = "zhanna123";
  public final String SECOND_FIRSTNAME = "zhanna";
  public final String SECOND_LASTNAME = "dictorova";
  public final String SECOND_PHONE_NUMBER = "375442222222";
  public final Payment SECOND_PAYMENT = Payment.CARD;
  public final Integer SECOND_RIDE_COUNT = 5;
  public final Double SECOND_SUM_OF_RATINGS = 5.0;
  public final Integer SECOND_NUMBER_OF_RATINGS = 1;
  public final Double SECOND_AVERAGE_RATING = SECOND_SUM_OF_RATINGS / SECOND_NUMBER_OF_RATINGS;

  public final Double START_AVERAGE_RATING =
      PassengerConstants.START_SUM_OF_RATINGS / PassengerConstants.START_NUMBER_OF_RATINGS;

  public PassengerRequest getFirstRequest() {
    return PassengerRequest.builder()
        .firstName(FIRST_FIRSTNAME)
        .lastName(FIRST_LASTNAME)
        .phoneNumber(FIRST_PHONE_NUMBER)
        .payment(FIRST_PAYMENT)
        .build();
  }

  public PassengerRequest getUpdateFirstRequest() {
    return PassengerRequest.builder()
        .firstName(SECOND_FIRSTNAME)
        .lastName(SECOND_LASTNAME)
        .phoneNumber(SECOND_PHONE_NUMBER)
        .payment(SECOND_PAYMENT)
        .build();
  }

  public List<Passenger> getPassengerList() {
    return List.of(getFirst(), getSecond());
  }

  public Passenger getFirst() {
    return Passenger.builder()
        .id(FIRST_ID)
        .username(FIRST_USERNAME)
        .firstName(FIRST_FIRSTNAME)
        .lastName(FIRST_LASTNAME)
        .phoneNumber(FIRST_PHONE_NUMBER)
        .payment(FIRST_PAYMENT)
        .rideCount(FIRST_RIDE_COUNT)
        .numberOfRatings(FIRST_NUMBER_OF_RATINGS)
        .sumOfRatings(FIRST_SUM_OF_RATINGS)
        .build();
  }

  public Passenger getSecond() {
    return Passenger.builder()
        .id(SECOND_ID)
        .username(SECOND_USERNAME)
        .firstName(SECOND_FIRSTNAME)
        .lastName(SECOND_LASTNAME)
        .phoneNumber(SECOND_PHONE_NUMBER)
        .payment(SECOND_PAYMENT)
        .rideCount(SECOND_RIDE_COUNT)
        .numberOfRatings(SECOND_NUMBER_OF_RATINGS)
        .sumOfRatings(SECOND_SUM_OF_RATINGS)
        .build();
  }

  public Passenger getCreatedFirst() {
    return Passenger.builder()
        .id(FIRST_ID)
        .username(FIRST_USERNAME)
        .firstName(FIRST_FIRSTNAME)
        .lastName(FIRST_LASTNAME)
        .phoneNumber(FIRST_PHONE_NUMBER)
        .payment(FIRST_PAYMENT)
        .rideCount(PassengerConstants.START_RIDE_COUNT)
        .sumOfRatings(PassengerConstants.START_SUM_OF_RATINGS)
        .numberOfRatings(PassengerConstants.START_NUMBER_OF_RATINGS)
        .build();
  }

  public Passenger getUpdatedFirst() {
    return Passenger.builder()
        .id(FIRST_ID)
        .username(FIRST_USERNAME)
        .firstName(SECOND_FIRSTNAME)
        .lastName(SECOND_LASTNAME)
        .phoneNumber(SECOND_PHONE_NUMBER)
        .payment(SECOND_PAYMENT)
        .rideCount(FIRST_RIDE_COUNT)
        .numberOfRatings(FIRST_NUMBER_OF_RATINGS)
        .sumOfRatings(FIRST_SUM_OF_RATINGS)
        .build();
  }

  public List<PassengerResponse> getResponseList() {
    return List.of(getFirstResponse(), getSecondResponse());
  }

  public PassengerResponse getFirstResponse() {
    return PassengerResponse.builder()
        .id(FIRST_ID)
        .username(FIRST_USERNAME)
        .firstName(FIRST_FIRSTNAME)
        .lastName(FIRST_LASTNAME)
        .phoneNumber(FIRST_PHONE_NUMBER)
        .payment(FIRST_PAYMENT)
        .rideCount(FIRST_RIDE_COUNT)
        .averageRating(FIRST_AVERAGE_RATING)
        .build();
  }

  public PassengerResponse getSecondResponse() {
    return PassengerResponse.builder()
        .id(SECOND_ID)
        .username(SECOND_USERNAME)
        .firstName(SECOND_FIRSTNAME)
        .lastName(SECOND_LASTNAME)
        .phoneNumber(SECOND_PHONE_NUMBER)
        .payment(SECOND_PAYMENT)
        .rideCount(SECOND_RIDE_COUNT)
        .averageRating(SECOND_AVERAGE_RATING)
        .build();
  }

  public PassengerResponse getCreatedFirstResponse() {
    return PassengerResponse.builder()
        .id(FIRST_ID)
        .username(FIRST_USERNAME)
        .firstName(FIRST_FIRSTNAME)
        .lastName(FIRST_LASTNAME)
        .phoneNumber(FIRST_PHONE_NUMBER)
        .payment(FIRST_PAYMENT)
        .rideCount(PassengerConstants.START_RIDE_COUNT)
        .averageRating(PassengerTestData.START_AVERAGE_RATING)
        .build();
  }

  public PassengerResponse getUpdatedFirstResponse() {
    return PassengerResponse.builder()
        .id(FIRST_ID)
        .username(FIRST_USERNAME)
        .firstName(SECOND_FIRSTNAME)
        .lastName(SECOND_LASTNAME)
        .phoneNumber(SECOND_PHONE_NUMBER)
        .payment(SECOND_PAYMENT)
        .rideCount(FIRST_RIDE_COUNT)
        .averageRating(FIRST_AVERAGE_RATING)
        .build();
  }
}
