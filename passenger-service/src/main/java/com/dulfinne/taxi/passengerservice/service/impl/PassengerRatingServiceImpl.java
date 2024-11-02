package com.dulfinne.taxi.passengerservice.service.impl;

import static com.dulfinne.taxi.passengerservice.mapper.PassengerRatingMapper.RATING_MAPPER_INSTANCE;
import com.dulfinne.taxi.passengerservice.dto.request.PassengerRatingRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import com.dulfinne.taxi.passengerservice.model.PassengerInfo;
import com.dulfinne.taxi.passengerservice.model.PassengerRating;
import com.dulfinne.taxi.passengerservice.repository.PassengerInfoRepository;
import com.dulfinne.taxi.passengerservice.repository.PassengerRatingRepository;
import com.dulfinne.taxi.passengerservice.service.PassengerRatingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PassengerRatingServiceImpl implements PassengerRatingService {

  private final PassengerRatingRepository passengerRatingRepository;
  private final PassengerInfoRepository passengerInfoRepository;

  @Transactional(readOnly = true)
  @Override
  public Page<PassengerRatingResponse> getPassengerRatings(
      Long passengerId, Integer offset, Integer limit, String sortField) {

    Page<PassengerRating> ratingPage =
        passengerRatingRepository.findByPassengerInfo_Id(
            passengerId, PageRequest.of(offset, limit, Sort.by(sortField)));

    return ratingPage.map(RATING_MAPPER_INSTANCE::toResponse);
  }

  @Transactional
  @Override
  public PassengerRatingResponse savePassengerRating(
      Long id, PassengerRatingRequest request) {
    PassengerRating passengerRating = RATING_MAPPER_INSTANCE.toEntity(request);

    PassengerInfo passengerInfo = getPassengerInfoIfExists(id);
    recalculateAndSaveAverageRating(passengerInfo, passengerRating.getRating());
    passengerRating.setPassengerInfo(passengerInfo);
    passengerRatingRepository.save(passengerRating);

    return RATING_MAPPER_INSTANCE.toResponse(passengerRating);
  }

  private void recalculateAndSaveAverageRating(PassengerInfo passengerInfo, Integer newRating) {
    passengerInfo.setAverageRating((passengerInfo.getAverageRating() + newRating) / 2);
  }

  private PassengerInfo getPassengerInfoIfExists(Long id) {
    return passengerInfoRepository
        .findById(id)
        .orElseThrow(
            () -> new EntityNotFoundException(String.format("Passenger not found: id = %d", id)));
  }
}
