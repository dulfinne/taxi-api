package com.dulfinne.taxi.passengerservice.service.impl;

import com.dulfinne.jooq.generated.tables.records.PassengerRatingRecord;
import com.dulfinne.jooq.generated.tables.records.PassengerRecord;
import com.dulfinne.taxi.avro.Rating;
import com.dulfinne.taxi.passengerservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import com.dulfinne.taxi.passengerservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.passengerservice.exception.IllegalSortFieldException;
import com.dulfinne.taxi.passengerservice.mapper.PassengerRatingMapper;
import com.dulfinne.taxi.passengerservice.model.sort.SortFieldRating;
import com.dulfinne.taxi.passengerservice.repository.PassengerRatingRepository;
import com.dulfinne.taxi.passengerservice.repository.PassengerRepository;
import com.dulfinne.taxi.passengerservice.service.PassengerRatingService;
import com.dulfinne.taxi.passengerservice.util.ExceptionKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PassengerRatingServiceImpl implements PassengerRatingService {

  private final PassengerRatingRepository passengerRatingRepository;
  private final PassengerRepository passengerRepository;
  private final PassengerRatingMapper passengerRatingMapper;

  @Transactional(readOnly = true)
  @Override
  public PaginatedResponse<PassengerRatingResponse> getPassengerRatings(
      String username, Integer offset, Integer limit, String sortField) {
    log.info("Getting all passenger ratings. Started. Username = {}", username);
    checkSortFieldIsValid(sortField);

    PassengerRecord passenger = getPassengerIfExistsByUsername(username);
    List<PassengerRatingResponse> ratingsPage =
        passengerRatingRepository
            .findByPassengerId(passenger.getId(), offset, limit, sortField)
            .stream()
            .map(passengerRatingMapper::toResponse)
            .toList();

    int totalElements = passengerRatingRepository.getTotalRecords();
    int totalPages = (int) Math.ceil((double) totalElements / limit);

    return new PaginatedResponse<>(ratingsPage, offset, limit, totalElements, totalPages);
  }

  @Transactional
  @Override
  public void savePassengerRating(Rating rating) {
    log.info("Saving passenger rating to database. Started. Username = {}", rating.getUsername());
    PassengerRecord passenger = getPassengerIfExistsByUsername(rating.getUsername());

    PassengerRatingRecord passengerRating = new PassengerRatingRecord();
    passengerRating.setPassengerId(passenger.getId());
    passengerRating.setRating(rating.getRating());
    passengerRating.setFeedback(rating.getFeedback());

    passenger.setNumberOfRatings(passenger.getNumberOfRatings() + 1);
    passenger.setSumOfRatings(
        passenger.getSumOfRatings().add(BigDecimal.valueOf(passengerRating.getRating())));
    passengerRatingRepository.save(passengerRating);
  }

  private PassengerRecord getPassengerIfExistsByUsername(String username) {
    return passengerRepository
        .findByUsername(username)
        .orElseThrow(
            () ->
                new EntityNotFoundException(ExceptionKeys.PASSENGER_NOT_FOUND_USERNAME, username));
  }

  private void checkSortFieldIsValid(String sortField) {
    boolean isValid =
        Arrays.stream(SortFieldRating.values())
            .anyMatch(field -> field.getValue().equals(sortField));

    if (!isValid) {
      throw new IllegalSortFieldException(ExceptionKeys.ILLEGAL_SORT_FIELD, sortField);
    }
  }
}
