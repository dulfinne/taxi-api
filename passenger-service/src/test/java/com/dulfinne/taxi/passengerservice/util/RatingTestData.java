package com.dulfinne.taxi.passengerservice.util;

import com.dulfinne.taxi.avro.Rating;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import com.dulfinne.taxi.passengerservice.model.PassengerRating;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RatingTestData {
  public static final Long FIRST_ID = 1L;
  public static final Integer FIRST_RATING = 2;
  public static final String FIRST_FEEDBACK = "test first feedback";

  public static final Long SECOND_ID = 2L;
  public static final Integer SECOND_RATING = 5;
  public static final String SECOND_FEEDBACK = "test second feedback";

  public static List<PassengerRatingResponse> getResponseList() {
    return List.of(getFirstResponse().build(), getSecondResponse().build());
  }

  public static PassengerRatingResponse.PassengerRatingResponseBuilder getFirstResponse() {
    return PassengerRatingResponse.builder()
        .id(FIRST_ID)
        .rating(FIRST_RATING)
        .feedback(FIRST_FEEDBACK);
  }

  public static PassengerRatingResponse.PassengerRatingResponseBuilder getSecondResponse() {
    return PassengerRatingResponse.builder()
        .id(SECOND_ID)
        .rating(SECOND_RATING)
        .feedback(SECOND_FEEDBACK);
  }

  public static List<PassengerRating> getRatingList() {
    return List.of(getFirst().build(), getSecond().build());
  }

  public static PassengerRating.PassengerRatingBuilder getFirst() {

    return PassengerRating.builder()
        .id(FIRST_ID)
        .passenger(PassengerTestData.getFirst().build())
        .rating(FIRST_RATING)
        .feedback(FIRST_FEEDBACK);
  }

  public static PassengerRating.PassengerRatingBuilder getSecond() {
    return PassengerRating.builder()
        .id(SECOND_ID)
        .passenger(PassengerTestData.getFirst().build())
        .rating(SECOND_RATING)
        .feedback(SECOND_FEEDBACK);
  }

  public static Rating.Builder getKafkaRating() {
    return Rating.newBuilder()
        .setUsername(PassengerTestData.FIRST_USERNAME)
        .setRating(FIRST_RATING)
        .setFeedback(FIRST_FEEDBACK);
  }
}
