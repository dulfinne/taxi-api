package com.dulfinne.taxi.paymentservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DescriptionConstants {

  public static final String WALLET_DEBIT = "Debited from wallet";

  public static final String WALLET_CREDIT = "Credited to wallet";

  public static final String RIDE_PAYMENT = "Payment for ride: %d";

  public static final String RIDE_PAYOUT = "Payout for ride: %d";

  public static final String DEBT_REPAYMENT = "Debt repayment";

  public static final String NOT_ENOUGH_MONEY = "Attempted payment for ride %d. Not enough money";
}
