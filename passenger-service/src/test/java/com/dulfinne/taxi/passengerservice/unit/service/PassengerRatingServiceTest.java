package com.dulfinne.taxi.passengerservice.unit.service;

import com.dulfinne.taxi.avro.Rating;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import com.dulfinne.taxi.passengerservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.passengerservice.exception.IllegalSortFieldException;
import com.dulfinne.taxi.passengerservice.model.Passenger;
import com.dulfinne.taxi.passengerservice.model.PassengerRating;
import com.dulfinne.taxi.passengerservice.repository.PassengerRatingRepository;
import com.dulfinne.taxi.passengerservice.repository.PassengerRepository;
import com.dulfinne.taxi.passengerservice.service.impl.PassengerRatingServiceImpl;
import com.dulfinne.taxi.passengerservice.util.PaginationTestData;
import com.dulfinne.taxi.passengerservice.util.PassengerTestData;
import com.dulfinne.taxi.passengerservice.util.RatingTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PassengerRatingServiceTest {

  @InjectMocks
  private PassengerRatingServiceImpl ratingService;

  @Mock
  private PassengerRatingRepository ratingRepository;
  @Mock
  private PassengerRepository passengerRepository;

  @Test
  void getPassengerRatings_whenValidParams_thenReturnRatingsPage() {
    String username = PassengerTestData.FIRST_USERNAME;
    Passenger passenger = PassengerTestData.getFirst().build();

    Page<PassengerRating> ratingsPage = new PageImpl<>(RatingTestData.getRatingList());
    List<PassengerRatingResponse> expectedContent = RatingTestData.getResponseList();

    // Arrange
    when(passengerRepository.findByUsername(any(String.class))).thenReturn(Optional.of(passenger));
    when(ratingRepository.findByPassengerId(any(Long.class), any(Pageable.class)))
        .thenReturn(ratingsPage);

    // Act
    Page<PassengerRatingResponse> actualPage =
        ratingService.getPassengerRatings(
            username,
            PaginationTestData.DEFAULT_OFFSET,
            PaginationTestData.DEFAULT_LIMIT,
            PaginationTestData.RATING_SORT_FIELD);

    // Assert
    assertEquals(ratingsPage.getTotalElements(), actualPage.getTotalElements());
    assertEquals(ratingsPage.getNumber(), actualPage.getNumber());
    assertEquals(ratingsPage.getSize(), actualPage.getSize());
    assertEquals(expectedContent, actualPage.getContent());

    verify(passengerRepository).findByUsername(any(String.class));
    verify(ratingRepository).findByPassengerId(any(Long.class), any(Pageable.class));
  }

  @Test
  void getPassengerRatings_whenInvalidSortField_thenThrowIllegalSortFieldException() {
    String username = PassengerTestData.FIRST_USERNAME;

    // Act & Assert
    assertThrows(
        IllegalSortFieldException.class,
        () ->
            ratingService.getPassengerRatings(
                username,
                PaginationTestData.DEFAULT_OFFSET,
                PaginationTestData.DEFAULT_LIMIT,
                PaginationTestData.INVALID_SORT_FIELD));
  }

  @Test
  void getPassengerRatings_whenPassengerNotFound_thenThrowEntityNotFoundException() {
    String username = PassengerTestData.FIRST_USERNAME;

    // Arrange
    when(passengerRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        EntityNotFoundException.class,
        () ->
            ratingService.getPassengerRatings(
                username,
                PaginationTestData.DEFAULT_OFFSET,
                PaginationTestData.DEFAULT_LIMIT,
                PaginationTestData.RATING_SORT_FIELD));
  }

  @Test
  void savePassengerRating_whenValidParams_thenSavePassengerRating() {
    Passenger passenger = PassengerTestData.getFirst().build();
    Rating ratingFromKafka = RatingTestData.getKafkaRating().build();
    Double expectedSum = passenger.getSumOfRatings() + ratingFromKafka.getRating();
    Integer expectedRatingCount = passenger.getNumberOfRatings() + 1;

    // Arrange
    when(passengerRepository.findByUsername(any(String.class))).thenReturn(Optional.of(passenger));

    // Act
    ratingService.savePassengerRating(ratingFromKafka);

    // Assert
    assertEquals(expectedSum, passenger.getSumOfRatings());
    assertEquals(expectedRatingCount, passenger.getNumberOfRatings());

    verify(passengerRepository).findByUsername(any(String.class));
    verify(ratingRepository).save(any(PassengerRating.class));
  }

  @Test
  void savePassengerRating_whenPassengerNotFound_thenThrowEntityNotFoundException() {
    Rating ratingFromKafka = RatingTestData.getKafkaRating().build();

    // Act & Assert
    assertThrows(
        EntityNotFoundException.class, () -> ratingService.savePassengerRating(ratingFromKafka));
  }
}
