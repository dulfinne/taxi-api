package com.dulfinne.taxi.driverservice.util;

import com.dulfinne.taxi.avro.Rating;
import com.dulfinne.taxi.driverservice.dto.response.DriverRatingResponse;
import com.dulfinne.taxi.driverservice.model.DriverRating;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RatingTestData {
  public final Long FIRST_ID = 1L;
  public final Integer FIRST_RATING = 2;
  public final String FIRST_FEEDBACK = "test first feedback";

  public static DriverRatingResponse getResponse() {
    return DriverRatingResponse.builder()
        .id(FIRST_ID)
        .rating(FIRST_RATING)
        .feedback(FIRST_FEEDBACK)
        .build();
  }

  public static DriverRating getRating() {

    return DriverRating.builder()
        .id(FIRST_ID)
        .driver(DriverTestData.getDriver())
        .rating(FIRST_RATING)
        .feedback(FIRST_FEEDBACK)
        .build();
  }

  public static Rating getKafkaRating() {
    return Rating.newBuilder()
        .setUsername(DriverTestData.USERNAME)
        .setRating(FIRST_RATING)
        .setFeedback(FIRST_FEEDBACK)
        .build();
  }
}
