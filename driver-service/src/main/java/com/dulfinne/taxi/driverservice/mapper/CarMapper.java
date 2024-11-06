package com.dulfinne.taxi.driverservice.mapper;

import com.dulfinne.taxi.driverservice.dto.request.CarRequest;
import com.dulfinne.taxi.driverservice.dto.response.CarResponse;
import com.dulfinne.taxi.driverservice.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {
  CarMapper CAR_MAPPER_INSTANCE = Mappers.getMapper(CarMapper.class);

  CarResponse toResponse(Car car);

  Car toEntity(CarRequest carRequest);

  void updateEntity(CarRequest carRequest, @MappingTarget Car car);
}
