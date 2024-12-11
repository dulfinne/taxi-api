package com.dulfinne.taxi.promocodeservice.mapper;

import com.dulfinne.taxi.promocodeservice.dto.request.PromocodeUsageRequest;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeUsageResponse;
import com.dulfinne.taxi.promocodeservice.model.PromocodeUsage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PromocodeUsageMapper {

  PromocodeUsageResponse toResponse(PromocodeUsage promocodeUsage);

  PromocodeUsage toEntity(PromocodeUsageRequest request);

  void updateEntity(PromocodeUsageRequest request, @MappingTarget PromocodeUsage promocodeUsage);
}
