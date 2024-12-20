package com.dulfinne.taxi.paymentservice.service;

import com.dulfinne.taxi.paymentservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.paymentservice.dto.response.TransactionResponse;
import com.dulfinne.taxi.paymentservice.model.Wallet;

import java.math.BigDecimal;

public interface TransactionService {

  PaginatedResponse<TransactionResponse> getAllTransactions(
      Integer offset, Integer limit, String sort, String order);

  PaginatedResponse<TransactionResponse> getTransactionsByUsername(
      String username, Integer offset, Integer limit, String sort, String order);

  void createTransaction(Wallet wallet, BigDecimal amount);
}
