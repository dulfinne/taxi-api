package com.dulfinne.taxi.driverservice.mapper;

import com.dulfinne.taxi.driverservice.dto.request.DriverRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverResponse;
import com.dulfinne.taxi.driverservice.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DriverMapper {

  @Mapping(
      target = "averageRating",
      expression =
          "java(calculateAverageRating(driver.getSumOfRatings(), driver.getNumberOfRatings()))")
  DriverResponse toResponse(Driver driver);

  Driver toEntity(DriverRequest driverRequest);

  void updateEntity(DriverRequest driverRequest, @MappingTarget Driver driver);

  default Double calculateAverageRating(Double sumOfRatings, Integer numberOfRatings) {
    return new BigDecimal(sumOfRatings / numberOfRatings)
        .setScale(2, java.math.RoundingMode.HALF_UP)
        .doubleValue();
  }
}
