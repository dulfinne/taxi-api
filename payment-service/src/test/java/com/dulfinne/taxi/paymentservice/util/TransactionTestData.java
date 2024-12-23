package com.dulfinne.taxi.paymentservice.util;

import com.dulfinne.taxi.paymentservice.dto.response.TransactionResponse;
import com.dulfinne.taxi.paymentservice.model.Transaction;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@UtilityClass
public class TransactionTestData {

  public static final Long TRANSACTION_ID = 1L;
  public static final BigDecimal TRANSACTION_AMOUNT = BigDecimal.TEN;
  public static final ZonedDateTime TRANSACTION_TIME = ZonedDateTime.now();
  public static final String WALLET_USERNAME = WalletTestData.WALLET_USERNAME;


  public static Transaction getTransaction() {
    return Transaction.builder()
            .id(TRANSACTION_ID)
            .wallet(WalletTestData.getWallet())
            .amount(TRANSACTION_AMOUNT)
            .transactionTime(TRANSACTION_TIME)
            .build();
  }

  public static TransactionResponse getTransactionResponse() {
    return TransactionResponse.builder()
            .id(TRANSACTION_ID)
            .username(WALLET_USERNAME)
            .amount(TRANSACTION_AMOUNT)
            .transactionTime(TRANSACTION_TIME)
            .build();
  }
}
