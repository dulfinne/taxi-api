package com.dulfinne.taxi.passengerservice.service.impl;

import static com.dulfinne.taxi.passengerservice.mapper.PassengerRatingMapper.RATING_MAPPER_INSTANCE;
import com.dulfinne.taxi.passengerservice.dto.request.PassengerRatingRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import com.dulfinne.taxi.passengerservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.passengerservice.exception.IllegalSortFieldException;
import com.dulfinne.taxi.passengerservice.model.Passenger;
import com.dulfinne.taxi.passengerservice.model.PassengerRating;
import com.dulfinne.taxi.passengerservice.model.sort.SortFieldRating;
import com.dulfinne.taxi.passengerservice.repository.PassengerRepository;
import com.dulfinne.taxi.passengerservice.repository.PassengerRatingRepository;
import com.dulfinne.taxi.passengerservice.service.PassengerRatingService;
import com.dulfinne.taxi.passengerservice.util.ExceptionKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class PassengerRatingServiceImpl implements PassengerRatingService {

  private final PassengerRatingRepository passengerRatingRepository;
  private final PassengerRepository passengerRepository;

  @Transactional(readOnly = true)
  @Override
  public Page<PassengerRatingResponse> getPassengerRatings(
      String username, Integer offset, Integer limit, String sortField) {
    checkSortFieldIsValid(sortField);

    Passenger passenger = getPassengerIfExistsByUsername(username);
    Page<PassengerRating> ratingPage =
        passengerRatingRepository.findByPassengerId(
            passenger.getId(), PageRequest.of(offset, limit, Sort.by(sortField)));

    return ratingPage.map(RATING_MAPPER_INSTANCE::toResponse);
  }

  @Transactional
  @Override
  public PassengerRatingResponse savePassengerRating(
      String username, PassengerRatingRequest request) {
    Passenger passenger = getPassengerIfExistsByUsername(username);
    PassengerRating passengerRating = RATING_MAPPER_INSTANCE.toEntity(request);

    passengerRating.setPassenger(passenger);
    passenger.setNumberOfRatings(passenger.getNumberOfRatings() + 1);
    passenger.setSumOfRatings(passenger.getSumOfRatings() + passengerRating.getRating());
    passengerRatingRepository.save(passengerRating);

    return RATING_MAPPER_INSTANCE.toResponse(passengerRating);
  }

  private Passenger getPassengerIfExistsByUsername(String username) {
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
