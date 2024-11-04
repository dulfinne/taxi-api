package com.dulfinne.taxi.driverservice.mapper;

import com.dulfinne.taxi.driverservice.dto.response.DriverResponse;
import com.dulfinne.taxi.driverservice.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DriverMapper {
  DriverMapper INFO_MAPPER_INSTANCE = Mappers.getMapper(DriverMapper.class);

  DriverResponse toResponse(Driver driver);

  Driver toEntity(DriverResponse driverResponse);
}
