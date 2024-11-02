package com.dulfinne.taxi.passengerservice.mapper;

import com.dulfinne.taxi.passengerservice.dto.request.PassengerInfoRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerInfoResponse;
import com.dulfinne.taxi.passengerservice.model.PassengerInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PassengerInfoMapper {
  PassengerInfoMapper INFO_MAPPER_INSTANCE = Mappers.getMapper(PassengerInfoMapper.class);

  PassengerInfoResponse toResponse(PassengerInfo entity);

  PassengerInfo toEntity(PassengerInfoRequest request);
}
