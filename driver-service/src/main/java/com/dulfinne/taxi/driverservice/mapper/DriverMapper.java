package com.dulfinne.taxi.driverservice.mapper;

import com.dulfinne.taxi.driverservice.dto.request.DriverRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverResponse;
import com.dulfinne.taxi.driverservice.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DriverMapper {
  DriverMapper DRIVER_MAPPER_INSTANCE = Mappers.getMapper(DriverMapper.class);

  DriverResponse toResponse(Driver driver);

  Driver toEntity(DriverRequest driverRequest);

  void updateEntity(DriverRequest driverRequest, @MappingTarget Driver driver);
}
