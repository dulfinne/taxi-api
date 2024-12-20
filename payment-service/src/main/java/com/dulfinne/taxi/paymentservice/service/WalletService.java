package com.dulfinne.taxi.paymentservice.service;

import com.dulfinne.taxi.paymentservice.dto.request.MoneyRequest;
import com.dulfinne.taxi.paymentservice.dto.response.CanPayByCardResponse;
import com.dulfinne.taxi.paymentservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.paymentservice.dto.response.WalletResponse;

public interface WalletService {
  PaginatedResponse<WalletResponse> getAllWallets(
      Integer offset, Integer limit, String sort, String order);

  WalletResponse getWalletByUsername(String username);

  WalletResponse createWallet(String username);

  WalletResponse creditMoney(String username, MoneyRequest request);

  WalletResponse debitMoney(String username, MoneyRequest request);

  WalletResponse repayDebt(String username);

  CanPayByCardResponse canPayWithCard(String username);
}
