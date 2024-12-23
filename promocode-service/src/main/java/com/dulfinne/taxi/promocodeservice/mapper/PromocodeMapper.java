package com.dulfinne.taxi.promocodeservice.mapper;

import com.dulfinne.taxi.promocodeservice.dto.request.PromocodeRequest;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeResponse;
import com.dulfinne.taxi.promocodeservice.model.DiscountType;
import com.dulfinne.taxi.promocodeservice.model.Promocode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PromocodeMapper {

  @Mapping(source = "type", target = "type", qualifiedByName = "getPromocodeTypeFromId")
  PromocodeResponse toResponse(Promocode promocode);

  @Mapping(source = "type", target = "type", qualifiedByName = "getPromocodeTypeId")
  @Mapping(target = "usageCount", constant = "0")
  Promocode toEntity(PromocodeRequest request);

  @Mapping(source = "type", target = "type", qualifiedByName = "getPromocodeTypeId")
  void updateEntity(PromocodeRequest request, @MappingTarget Promocode promocode);

  @Named("getPromocodeTypeFromId")
  default DiscountType getPromocodeType(int id) {
    return DiscountType.fromId(id);
  }

  @Named("getPromocodeTypeId")
  default int getIdFromPromocodeType(DiscountType type) {
    return type.getId();
  }
}
