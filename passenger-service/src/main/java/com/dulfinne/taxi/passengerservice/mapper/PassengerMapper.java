package com.dulfinne.taxi.passengerservice.mapper;

import com.dulfinne.jooq.generated.public_.tables.records.PassengerRecord;
import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassengerMapper {

  @Mapping(
      target = "averageRating",
      expression =
          "java(calculateAverageRating(entity.getSumOfRatings(), entity.getNumberOfRatings()))")
  PassengerResponse toResponse(PassengerRecord entity);

  PassengerRecord toEntity(PassengerRequest request);

  void updateEntity(PassengerRequest request, @MappingTarget PassengerRecord entity);

  default Double calculateAverageRating(BigDecimal sumOfRatings, Integer numberOfRatings) {
    return sumOfRatings
        .divide(BigDecimal.valueOf(numberOfRatings), 2, RoundingMode.HALF_UP)
        .doubleValue();
  }
}
