package com.dulfinne.taxi.driverservice.mapper;

import com.dulfinne.taxi.driverservice.dto.request.DriverRatingRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverRatingResponse;
import com.dulfinne.taxi.driverservice.model.DriverRating;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DriverRatingMapper {
  DriverRatingMapper RATING_MAPPER_INSTANCE = Mappers.getMapper(DriverRatingMapper.class);

  DriverRatingResponse toResponse(DriverRating driverRating);

  DriverRating toEntity(DriverRatingRequest driverRatingRequest);
}
