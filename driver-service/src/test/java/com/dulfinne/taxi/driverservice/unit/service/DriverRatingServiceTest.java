package com.dulfinne.taxi.driverservice.unit.service;

import com.dulfinne.taxi.avro.Rating;
import com.dulfinne.taxi.driverservice.dto.response.DriverRatingResponse;
import com.dulfinne.taxi.driverservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.driverservice.mapper.DriverRatingMapper;
import com.dulfinne.taxi.driverservice.model.Driver;
import com.dulfinne.taxi.driverservice.model.DriverRating;
import com.dulfinne.taxi.driverservice.repository.DriverRatingRepository;
import com.dulfinne.taxi.driverservice.repository.DriverRepository;
import com.dulfinne.taxi.driverservice.service.impl.DriverRatingServiceImpl;
import com.dulfinne.taxi.driverservice.util.DriverTestData;
import com.dulfinne.taxi.driverservice.util.PaginationTestData;
import com.dulfinne.taxi.driverservice.util.RatingTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DriverRatingServiceTest {

  @InjectMocks private DriverRatingServiceImpl ratingService;
  @Mock private DriverRatingRepository ratingRepository;
  @Mock private DriverRepository driverRepository;
  @Spy private DriverRatingMapper ratingMapper = Mappers.getMapper(DriverRatingMapper.class);

  @Test
  void getAllDriverRatings_whenValidRequest_thenReturnDriverRatingsPage() {
    String username = DriverTestData.USERNAME;
    Driver driver = DriverTestData.getDriver();
    DriverRating rating = RatingTestData.getRating();
    DriverRatingResponse response = RatingTestData.getResponse();
    Page<DriverRating> ratingsPage = new PageImpl<>(List.of(rating, rating));
    Page<DriverRatingResponse> expectedPage = new PageImpl<>(List.of(response, response));

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));
    when(ratingRepository.findByDriverId(any(Long.class), any(Pageable.class)))
        .thenReturn(ratingsPage);

    // Act
    Page<DriverRatingResponse> resultPage =
        ratingService.getAllDriverRatings(
            username,
            PaginationTestData.DEFAULT_OFFSET,
            PaginationTestData.DEFAULT_LIMIT,
            PaginationTestData.RATING_SORT_FIELD);

    // Assert
    assertEquals(expectedPage.getTotalPages(), resultPage.getTotalPages());
    assertEquals(expectedPage.getSize(), resultPage.getSize());
    assertEquals(expectedPage.getNumberOfElements(), resultPage.getNumberOfElements());
    assertEquals(expectedPage.getContent(), resultPage.getContent());

    verify(driverRepository, times(1)).findByUsername(any(String.class));
    verify(ratingRepository, times(1)).findByDriverId(any(Long.class), any(Pageable.class));
    verify(ratingMapper, times(expectedPage.getNumberOfElements()))
        .toResponse(any(DriverRating.class));
  }

  @Test
  void getAllDriverRatings_whenDriverNotFoundByUsername_thenThrowEntityNotFoundException() {
    String username = DriverTestData.USERNAME;

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        EntityNotFoundException.class,
        () ->
            ratingService.getAllDriverRatings(
                username,
                PaginationTestData.DEFAULT_OFFSET,
                PaginationTestData.DEFAULT_LIMIT,
                PaginationTestData.RATING_SORT_FIELD));

    verify(driverRepository, times(1)).findByUsername(any(String.class));
  }

  @Test
  void saveDriverRating_whenValidParams_thenSaveDriverRating() {
    Driver driver = DriverTestData.getDriver();
    Rating ratingFromKafka = RatingTestData.getKafkaRating();
    Double expectedSum = driver.getSumOfRatings() + ratingFromKafka.getRating();
    Integer expectedRatingCount = driver.getNumberOfRatings() + 1;

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.of(driver));

    // Act
    ratingService.saveDriverRating(ratingFromKafka);

    // Assert
    assertEquals(expectedSum, driver.getSumOfRatings());
    assertEquals(expectedRatingCount, driver.getNumberOfRatings());

    verify(driverRepository, times(1)).findByUsername(any(String.class));
    verify(ratingRepository, times(1)).save(any(DriverRating.class));
  }

  @Test
  void saveDriverRating_whenDriverNotFoundByUsername_thenThrowEntityNotFoundException() {
    Rating ratingFromKafka = RatingTestData.getKafkaRating();

    // Arrange
    when(driverRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        EntityNotFoundException.class, () -> ratingService.saveDriverRating(ratingFromKafka));

    verify(driverRepository, times(1)).findByUsername(any(String.class));
  }
}
