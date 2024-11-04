package com.dulfinne.taxi.driverservice.mapper;

import com.dulfinne.taxi.driverservice.dto.response.DriverInfoResponse;
import com.dulfinne.taxi.driverservice.model.DriverInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DriverInfoMapper {
  DriverInfoMapper INFO_MAPPER_INSTANCE = Mappers.getMapper(DriverInfoMapper.class);

  DriverInfoResponse toResponse(DriverInfo driverInfo);

  DriverInfo toEntity(DriverInfoResponse driverInfoResponse);
}
