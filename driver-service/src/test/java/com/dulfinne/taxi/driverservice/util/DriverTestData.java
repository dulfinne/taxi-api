package com.dulfinne.taxi.driverservice.util;

import com.dulfinne.taxi.driverservice.dto.request.DriverRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverResponse;
import com.dulfinne.taxi.driverservice.model.Driver;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DriverTestData {

  public static final Long ID = 1L;
  public static final String USERNAME = "anna123";
  public static final String FIRST_NAME = "anna";
  public static final String LAST_NAME = "victorova";
  public static final String PHONE_NUMBER = "+375441111111";
  public static final Integer EXPERIENCE = 4;
  public static final Double SUM_OF_RATINGS = 25.0;
  public static final Integer NUMBER_OF_RATINGS = 5;
  public static final Double AVERAGE_RATING = SUM_OF_RATINGS / NUMBER_OF_RATINGS;

  public static final String UPDATED_FIRST_NAME = "zhanna";
  public static final String UPDATED_LAST_NAME = "dictorova";
  public static final String UPDATED_PHONE_NUMBER = "+375442222222";
  public static final Integer UPDATED_EXPERIENCE = 7;

  public static final Double START_SUM_OF_RATINGS = 5.0;
  public static final Integer START_NUMBER_OF_RATINGS = 1;
  public static final Double START_AVERAGE_RATING = START_SUM_OF_RATINGS / START_NUMBER_OF_RATINGS;

  public static Driver.DriverBuilder getDriver() {
    return Driver.builder()
        .id(ID)
        .username(USERNAME)
        .firstName(FIRST_NAME)
        .lastName(LAST_NAME)
        .phoneNumber(PHONE_NUMBER)
        .experience(EXPERIENCE)
        .sumOfRatings(SUM_OF_RATINGS)
        .numberOfRatings(NUMBER_OF_RATINGS);
  }

  public static DriverResponse.DriverResponseBuilder getResponse() {
    return DriverResponse.builder()
        .id(ID)
        .username(USERNAME)
        .firstName(FIRST_NAME)
        .lastName(LAST_NAME)
        .phoneNumber(PHONE_NUMBER)
        .experience(EXPERIENCE.toString())
        .averageRating(AVERAGE_RATING);
  }

  public static DriverRequest.DriverRequestBuilder getCreateRequest() {
    return DriverRequest.builder()
        .firstName(FIRST_NAME)
        .lastName(LAST_NAME)
        .experience(EXPERIENCE)
        .phoneNumber(PHONE_NUMBER);
  }

  public static DriverRequest.DriverRequestBuilder getUpdateRequest() {
    return DriverRequest.builder()
        .firstName(UPDATED_FIRST_NAME)
        .lastName(UPDATED_LAST_NAME)
        .experience(UPDATED_EXPERIENCE)
        .phoneNumber(UPDATED_PHONE_NUMBER);
  }

  public static DriverResponse.DriverResponseBuilder getUpdatedResponse() {
    return DriverResponse.builder()
        .id(ID)
        .username(USERNAME)
        .firstName(UPDATED_FIRST_NAME)
        .lastName(UPDATED_LAST_NAME)
        .phoneNumber(UPDATED_PHONE_NUMBER)
        .experience(UPDATED_EXPERIENCE.toString())
        .averageRating(START_AVERAGE_RATING);
  }
}
