package com.dulfinne.taxi.paymentservice.util;

import com.dulfinne.taxi.paymentservice.dto.response.WalletResponse;
import com.dulfinne.taxi.paymentservice.model.Wallet;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class WalletTestData {

  public static final Long WALLET_ID = 1L;
  public static final String WALLET_USERNAME = "anna123";
  public static final BigDecimal WALLET_BALANCE = BigDecimal.valueOf(100).setScale(2);
  public static final BigDecimal WALLET_DEBT = BigDecimal.valueOf(30).setScale(2);

  public static final Long SECOND_WALLET_ID = 4L;
  public static final String SECOND_WALLET_USERNAME = "zhanna321";
  public static final String NO_DEBT_WALLET_USERNAME = "mike0";
  public static final String DEBT_EXCEED_BALANCE_WALLET_USERNAME = "johndoe";
  public static final String ADMIN_USERNAME = "admin";
  public static final String NON_EXISTING_WALLET_USERNAME = "notfound123";

  public static final BigDecimal START_BALANCE = BigDecimal.valueOf(0).setScale(2);
  public static final BigDecimal START_DEBT = BigDecimal.valueOf(0).setScale(2);

  public static final BigDecimal INVALID_MONEY_AMOUNT = BigDecimal.valueOf(2.5).setScale(2);
  public static final BigDecimal CREDIT_MONEY_AMOUNT = BigDecimal.valueOf(50).setScale(2);
  public static final BigDecimal TOO_BIG_DEBIT_MONEY_AMOUNT = BigDecimal.valueOf(200).setScale(2);
  public static final BigDecimal NORMAL_DEBIT_MONEY_AMOUNT = BigDecimal.valueOf(60).setScale(2);

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

  public static WalletResponse getSecondWalletResponse() {
    return WalletResponse.builder()
        .id(SECOND_WALLET_ID)
        .username(SECOND_WALLET_USERNAME)
        .balance(START_BALANCE)
        .debt(START_DEBT)
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
