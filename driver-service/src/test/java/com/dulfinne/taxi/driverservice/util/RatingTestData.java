package com.dulfinne.taxi.driverservice.util;

import com.dulfinne.taxi.avro.Rating;
import com.dulfinne.taxi.driverservice.dto.response.DriverRatingResponse;
import com.dulfinne.taxi.driverservice.model.DriverRating;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RatingTestData {
  public static final Long FIRST_ID = 1L;
  public static final Integer FIRST_RATING = 2;
  public static final String FIRST_FEEDBACK = "test first feedback";

  public static DriverRatingResponse.DriverRatingResponseBuilder getResponse() {
    return DriverRatingResponse.builder()
        .id(FIRST_ID)
        .rating(FIRST_RATING)
        .feedback(FIRST_FEEDBACK);
  }

  public static DriverRating.DriverRatingBuilder getRating() {

    return DriverRating.builder()
        .id(FIRST_ID)
        .driver(DriverTestData.getDriver().build())
        .rating(FIRST_RATING)
        .feedback(FIRST_FEEDBACK);
  }

  public static Rating.Builder getKafkaRating() {
    return Rating.newBuilder()
        .setUsername(DriverTestData.USERNAME)
        .setRating(FIRST_RATING)
        .setFeedback(FIRST_FEEDBACK);
  }
}
