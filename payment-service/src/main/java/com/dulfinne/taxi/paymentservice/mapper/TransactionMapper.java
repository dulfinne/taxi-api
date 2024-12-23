package com.dulfinne.taxi.paymentservice.mapper;

import com.dulfinne.taxi.paymentservice.dto.response.TransactionResponse;
import com.dulfinne.taxi.paymentservice.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {

  @Mapping(source = "wallet.username", target = "username")
  TransactionResponse toResponse(Transaction transaction);
}
