package com.dulfinne.taxi.passengerservice.mapper;

import com.dulfinne.jooq.generated.tables.records.PassengerRatingRecord;
import com.dulfinne.taxi.passengerservice.dto.request.PassengerRatingRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerRatingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassengerRatingMapper {
  PassengerRatingResponse toResponse(PassengerRatingRecord entity);

  PassengerRatingRecord toEntity(PassengerRatingRequest request);
}
