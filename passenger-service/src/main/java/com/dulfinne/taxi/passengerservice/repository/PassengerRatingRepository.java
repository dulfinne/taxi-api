package com.dulfinne.taxi.passengerservice.repository;

import com.dulfinne.jooq.generated.tables.PassengerRating;
import com.dulfinne.jooq.generated.tables.records.PassengerRatingRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PassengerRatingRepository {

  private final DSLContext dsl;

  public List<PassengerRatingRecord> findByPassengerId(
      long passengerId, int offset, int limit, String sortBy) {
    var sortField =
        switch (sortBy) {
          case "rating" -> PassengerRating.PASSENGER_RATING.RATING;
          default -> PassengerRating.PASSENGER_RATING.ID;
        };

    return dsl.selectFrom(PassengerRating.PASSENGER_RATING)
        .where(PassengerRating.PASSENGER_RATING.PASSENGER_ID.eq(passengerId))
        .orderBy(sortField)
        .offset(offset * limit)
        .limit(limit)
        .fetch();
  }

  public void save(PassengerRatingRecord ratingRecord) {
    dsl.insertInto(PassengerRating.PASSENGER_RATING)
            .set(ratingRecord)
            .execute();
  }
}
