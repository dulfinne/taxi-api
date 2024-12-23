package com.dulfinne.taxi.paymentservice.util;

import com.dulfinne.taxi.paymentservice.dto.response.WalletResponse;
import com.dulfinne.taxi.paymentservice.model.Wallet;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class WalletTestData {

  public static final Long WALLET_ID = 1L;
  public static final String WALLET_USERNAME = "anna123";
  public static final BigDecimal WALLET_BALANCE = BigDecimal.valueOf(100);
  public static final BigDecimal WALLET_DEBT = BigDecimal.valueOf(30);

  public static final BigDecimal START_BALANCE = BigDecimal.valueOf(0);
  public static final BigDecimal START_DEBT = BigDecimal.valueOf(0);

  public static final BigDecimal CREDIT_MONEY_AMOUNT = BigDecimal.valueOf(50);
  public static final BigDecimal TOO_BIG_DEBIT_MONEY_AMOUNT = BigDecimal.valueOf(200);
  public static final BigDecimal NORMAL_DEBIT_MONEY_AMOUNT = BigDecimal.valueOf(60);

  public Wallet getWallet() {
    return Wallet.builder()
        .id(WALLET_ID)
        .username(WALLET_USERNAME)
        .balance(WALLET_BALANCE)
        .debt(WALLET_DEBT)
        .build();
  }

  public static WalletResponse getWalletResponse() {
    return WalletResponse.builder()
        .id(WALLET_ID)
        .username(WALLET_USERNAME)
        .balance(WALLET_BALANCE)
        .debt(WALLET_DEBT)
        .build();
  }

  public Wallet getCreatedWallet() {
    return Wallet.builder()
        .id(WALLET_ID)
        .username(WALLET_USERNAME)
        .balance(START_BALANCE)
        .debt(START_DEBT)
        .build();
  }

  public static WalletResponse getCreatedWalletResponse() {
    return WalletResponse.builder()
        .id(WALLET_ID)
        .username(WALLET_USERNAME)
        .balance(START_BALANCE)
        .debt(START_DEBT)
        .build();
  }

  public static WalletResponse getCreditedWalletResponse() {
    return WalletResponse.builder()
        .id(WALLET_ID)
        .username(WALLET_USERNAME)
        .balance(WALLET_BALANCE.add(CREDIT_MONEY_AMOUNT))
        .debt(WALLET_DEBT)
        .build();
  }

  public static WalletResponse getDebitedWalletResponse() {
    return WalletResponse.builder()
        .id(WALLET_ID)
        .username(WALLET_USERNAME)
        .balance(WALLET_BALANCE.subtract(NORMAL_DEBIT_MONEY_AMOUNT))
        .debt(WALLET_DEBT)
        .build();
  }

  public static WalletResponse getRepayedWalletResponse() {
    return WalletResponse.builder()
        .id(WALLET_ID)
        .username(WALLET_USERNAME)
        .balance(WALLET_BALANCE.subtract(WALLET_DEBT))
        .debt(START_DEBT)
        .build();
  }
}
