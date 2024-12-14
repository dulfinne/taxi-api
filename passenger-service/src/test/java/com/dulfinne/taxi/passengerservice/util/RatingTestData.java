package com.dulfinne.taxi.passengerservice.util;

import com.dulfinne.taxi.avro.Rating;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import com.dulfinne.taxi.passengerservice.model.PassengerRating;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class RatingTestData {
  public final Long FIRST_ID = 1L;
  public final Integer FIRST_RATING = 2;
  public final String FIRST_FEEDBACK = "test first feedback";

  public final Long SECOND_ID = 2L;
  public final Integer SECOND_RATING = 5;
  public final String SECOND_FEEDBACK = "test second feedback";

  public static List<PassengerRatingResponse> getResponseList() {
    return List.of(getFirstResponse(), getSecondResponse());
  }

  public static PassengerRatingResponse getFirstResponse() {
    return PassengerRatingResponse.builder()
        .id(FIRST_ID)
        .rating(FIRST_RATING)
        .feedback(FIRST_FEEDBACK)
        .build();
  }

  public static PassengerRatingResponse getSecondResponse() {
    return PassengerRatingResponse.builder()
        .id(SECOND_ID)
        .rating(SECOND_RATING)
        .feedback(SECOND_FEEDBACK)
        .build();
  }

  public static List<PassengerRating> getRatingList() {
    return List.of(getFirst(), getSecond());
  }

  public static PassengerRating getFirst() {

    return PassengerRating.builder()
        .id(FIRST_ID)
        .passenger(PassengerTestData.getFirst())
        .rating(FIRST_RATING)
        .feedback(FIRST_FEEDBACK)
        .build();
  }

  public static PassengerRating getSecond() {
    return PassengerRating.builder()
        .id(SECOND_ID)
        .passenger(PassengerTestData.getFirst())
        .rating(SECOND_RATING)
        .feedback(SECOND_FEEDBACK)
        .build();
  }

  public static Rating getKafkaRating(){
    return Rating.newBuilder()
            .setUsername(PassengerTestData.FIRST_USERNAME)
            .setRating(FIRST_RATING)
            .setFeedback(FIRST_FEEDBACK)
            .build();
  }
}
