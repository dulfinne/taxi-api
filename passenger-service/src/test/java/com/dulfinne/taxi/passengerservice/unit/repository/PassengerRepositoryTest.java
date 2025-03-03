package com.dulfinne.taxi.passengerservice.unit.repository;

import com.dulfinne.jooq.generated.tables.Passenger;
import com.dulfinne.jooq.generated.tables.records.PassengerRecord;
import com.dulfinne.taxi.passengerservice.repository.PassengerRepository;
import com.dulfinne.taxi.passengerservice.util.PaginationTestData;
import com.dulfinne.taxi.passengerservice.util.PassengerTestData;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.DeleteConditionStep;
import org.jooq.DeleteUsingStep;
import org.jooq.InsertResultStep;
import org.jooq.InsertSetMoreStep;
import org.jooq.InsertSetStep;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.UpdateConditionStep;
import org.jooq.UpdateResultStep;
import org.jooq.UpdateSetFirstStep;
import org.jooq.UpdateSetMoreStep;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PassengerRepositoryTest {

  @Mock private DSLContext dsl;

  @Mock private SelectSelectStep selectSelectStep;

  @Mock private SelectJoinStep selectJoinStep;

  @Mock private SelectConditionStep selectConditionStep;

  @Mock private InsertSetStep insertSetStep;

  @Mock private InsertResultStep insertResultStep;

  @Mock private InsertSetMoreStep insertSetMoreStep;

  @Mock private SelectWhereStep selectWhereStep;

  @Mock private UpdateSetFirstStep updateSetFirstStep;

  @Mock private UpdateSetMoreStep updateSetMoreStep;

  @Mock private UpdateConditionStep updateConditionStep;

  @Mock private UpdateResultStep updateResultStep;

  @Mock private DeleteUsingStep deleteUsingStep;

  @Mock private DeleteConditionStep deleteConditionStep;

  @InjectMocks private PassengerRepository passengerRepository;

  @Test
  void testGetTotalRecords() {
    when(dsl.selectCount()).thenReturn(selectSelectStep);
    when(selectSelectStep.from(Passenger.PASSENGER)).thenReturn(selectJoinStep);
    when(selectJoinStep.fetchOne(0, Integer.class))
        .thenReturn(PaginationTestData.DEFAULT_TOTAL_ELEMENTS);

    Integer totalRecords = passengerRepository.getTotalRecords();

    assertEquals(PaginationTestData.DEFAULT_TOTAL_ELEMENTS, totalRecords);
  }

  @Test
  void testFindByUsername() {
    PassengerRecord record = PassengerTestData.getFirst();
    String username = PassengerTestData.FIRST_USERNAME;
    Condition condition = Passenger.PASSENGER.USERNAME.eq(username);

    when(dsl.selectFrom(Passenger.PASSENGER)).thenReturn(selectWhereStep);
    when(selectWhereStep.where(condition)).thenReturn(selectConditionStep);
    when(selectConditionStep.fetchOne()).thenReturn(record);

    Optional<PassengerRecord> foundPassenger = passengerRepository.findByUsername(username);

    assertTrue(foundPassenger.isPresent());
    assertEquals(record, foundPassenger.get());
  }

  @Test
  void testSave() {
    PassengerRecord record = PassengerTestData.getFirst();

    when(dsl.insertInto(Passenger.PASSENGER)).thenReturn(insertSetStep);
    when(insertSetStep.set(record)).thenReturn(insertSetMoreStep);
    when(insertSetMoreStep.returning()).thenReturn(insertResultStep);
    when(insertResultStep.fetchOne()).thenReturn(record);

    PassengerRecord savedPassenger = passengerRepository.save(record);

    assertNotNull(savedPassenger);
    assertEquals(record, savedPassenger);
  }

  @Test
  void testUpdate() {
    PassengerRecord record = PassengerTestData.getFirst();
    String username = PassengerTestData.FIRST_USERNAME;
    Condition condition = Passenger.PASSENGER.USERNAME.eq(username);

    when(dsl.update(Passenger.PASSENGER)).thenReturn(updateSetFirstStep);
    when(updateSetFirstStep.set(record)).thenReturn(updateSetMoreStep);
    when(updateSetMoreStep.where(condition)).thenReturn(updateConditionStep);
    when(updateConditionStep.returning()).thenReturn(updateResultStep);
    when(updateResultStep.fetchOne()).thenReturn(record);

    PassengerRecord updatedPassenger = passengerRepository.update(username, record);

    assertNotNull(updatedPassenger);
    assertEquals(record, updatedPassenger);
  }

  @Test
  void testDelete() {
    String username = PassengerTestData.FIRST_USERNAME;
    Condition condition = Passenger.PASSENGER.USERNAME.eq(username);

    when(dsl.delete(Passenger.PASSENGER)).thenReturn(deleteUsingStep);
    when(deleteUsingStep.where(condition)).thenReturn(deleteConditionStep);

    passengerRepository.delete(username);
    verify(dsl).delete(Passenger.PASSENGER);
  }
}
