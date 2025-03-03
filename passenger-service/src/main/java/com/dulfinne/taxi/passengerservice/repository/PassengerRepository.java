package com.dulfinne.taxi.passengerservice.repository;

import com.dulfinne.jooq.generated.tables.Passenger;
import com.dulfinne.jooq.generated.tables.records.PassengerRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.Field;
import org.springframework.stereotype.Repository;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PassengerRepository {

  private final DSLContext dsl;

  private static Field<?> getSortField(String sortBy) {
    return switch (sortBy) {
      case "firstName" -> Passenger.PASSENGER.FIRST_NAME;
      case "lastName" -> Passenger.PASSENGER.LAST_NAME;
      case "username" -> Passenger.PASSENGER.USERNAME;
      default -> Passenger.PASSENGER.ID;
    };
  }

  public List<PassengerRecord> findAll(int pageNumber, int pageSize, String sortBy) {
    var sortField = getSortField(sortBy);

    return dsl.selectFrom(Passenger.PASSENGER)
        .orderBy(sortField)
        .offset(pageNumber * pageSize)
        .limit(pageSize)
        .fetch();
  }

  public Integer getTotalRecords() {
    return dsl.selectCount().
            from(Passenger.PASSENGER)
            .fetchOne(0, Integer.class);
  }

  public Optional<PassengerRecord> findByUsername(String username) {
    PassengerRecord passengerRecord =
        dsl.selectFrom(Passenger.PASSENGER)
            .where(Passenger.PASSENGER.USERNAME.eq(username))
            .fetchOne();

    return Optional.ofNullable(passengerRecord);
  }

  public PassengerRecord save(PassengerRecord passengerRecord) {
    return dsl.insertInto(Passenger.PASSENGER)
            .set(passengerRecord)
            .returning()
            .fetchOne();
  }

  public PassengerRecord update(String username, PassengerRecord passengerRecord) {
    return dsl.update(Passenger.PASSENGER)
        .set(passengerRecord)
        .where(Passenger.PASSENGER.USERNAME.eq(username))
        .returning()
        .fetchOne();
  }

  public Optional<PassengerRecord> findByPhoneNumber(String phoneNumber) {
    PassengerRecord passengerRecord =
        dsl.selectFrom(Passenger.PASSENGER)
            .where(Passenger.PASSENGER.PHONE_NUMBER.eq(phoneNumber))
            .fetchOne();
    return Optional.ofNullable(passengerRecord);
  }

  public void delete(String username) {
    dsl.delete(Passenger.PASSENGER)
            .where(Passenger.PASSENGER.USERNAME.eq(username))
            .execute();
  }
}
