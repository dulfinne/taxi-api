package com.dulfinne.taxi.passengerservice.repository;

import com.dulfinne.jooq.generated.tables.Passenger;
import com.dulfinne.jooq.generated.tables.PassengerRating;
import com.dulfinne.jooq.generated.tables.records.PassengerRatingRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PassengerRatingRepository {

  private final DSLContext dsl;

    private static Field<?> getSortField(String sortBy) {
        return switch (sortBy) {
            case "rating" -> PassengerRating.PASSENGER_RATING.RATING;
            default -> PassengerRating.PASSENGER_RATING.ID;
        };
    }

  public List<PassengerRatingRecord> findByPassengerId(
      long passengerId, int offset, int limit, String sortBy) {
    var sortField = getSortField(sortBy);

    return dsl.selectFrom(PassengerRating.PASSENGER_RATING)
        .where(PassengerRating.PASSENGER_RATING.PASSENGER_ID.eq(passengerId))
        .orderBy(sortField)
        .offset(offset * limit)
        .limit(limit)
        .fetch();
  }

    public Integer getTotalRecords() {
        return dsl.selectCount()
                .from(Passenger.PASSENGER)
                .fetchOne(0, Integer.class);
    }

  public void save(PassengerRatingRecord ratingRecord) {
    dsl.insertInto(PassengerRating.PASSENGER_RATING)
            .set(ratingRecord)
            .execute();
  }
}
