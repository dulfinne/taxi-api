package com.dulfinne.taxi.paymentservice.mapper;

import com.dulfinne.taxi.paymentservice.dto.response.WalletResponse;
import com.dulfinne.taxi.paymentservice.model.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WalletMapper {

  @Mapping(target = "balance", expression = "java(setScale(wallet.getBalance()))")
  @Mapping(target = "debt", expression = "java(setScale(wallet.getDebt()))")
  WalletResponse toResponse(Wallet wallet);

  default BigDecimal setScale(BigDecimal value) {
    return value.setScale(2, RoundingMode.HALF_UP);
  }
}
