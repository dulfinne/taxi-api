package com.dulfinne.taxi.paymentservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionKeys {

  public static final String WALLET_NOT_FOUND_USERNAME = "wallet-not-found-username";
  public static final String WALLET_EXISTS_USERNAME = "wallet-exists-username";
  public static final String DEBT_IS_ZERO = "debt-is-zero";
  public static final String NOT_ENOUGH_MONEY_DEBT = "not-enough-money-debt";
  public static final String NOT_ENOUGH_MONEY_DEBIT = "not-enough-money-debit";
  public static final String UNKNOWN_ERROR = "unknown-error";
  public static final String INVALID_MONEY_AMOUNT_LIMITS = "invalid-money-amount-limits";
}
