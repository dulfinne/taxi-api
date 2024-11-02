package com.dulfinne.taxi.passengerservice.mapper;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRatingRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import com.dulfinne.taxi.passengerservice.model.PassengerRating;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PassengerRatingMapper {
  PassengerRatingMapper RATING_MAPPER_INSTANCE = Mappers.getMapper(PassengerRatingMapper.class);

  PassengerRatingResponse toResponse(PassengerRating entity);

  PassengerRating toEntity(PassengerRatingRequest request);
}
