package com.dulfinne.taxi.passengerservice.mapper;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import com.dulfinne.taxi.passengerservice.model.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper
public interface PassengerMapper {
  PassengerMapper INFO_MAPPER_INSTANCE = Mappers.getMapper(PassengerMapper.class);

  @Mapping(
      target = "averageRating",
      expression =
          "java(calculateAverageRating(entity.getSumOfRatings(), entity.getNumberOfRatings()))")
  PassengerResponse toResponse(Passenger entity);

  Passenger toEntity(PassengerRequest request);

  void updateEntity(PassengerRequest request, @MappingTarget Passenger entity);

  default Double calculateAverageRating(Double sumOfRatings, Integer numberOfRatings) {
    return new BigDecimal(sumOfRatings / numberOfRatings)
        .setScale(2, java.math.RoundingMode.HALF_UP)
        .doubleValue();
  }
}
