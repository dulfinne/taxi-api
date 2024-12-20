package com.dulfinne.taxi.paymentservice.service.impl;

import com.dulfinne.taxi.paymentservice.dto.request.PaymentRequest;
import com.dulfinne.taxi.paymentservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.paymentservice.model.Wallet;
import com.dulfinne.taxi.paymentservice.repository.WalletRepository;
import com.dulfinne.taxi.paymentservice.service.PaymentService;
import com.dulfinne.taxi.paymentservice.service.TransactionService;
import com.dulfinne.taxi.paymentservice.util.ExceptionKeys;
import com.dulfinne.taxi.paymentservice.util.PaymentConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
  private final WalletRepository walletRepository;
  private final TransactionService transactionService;

  @Transactional
  @Override
  public void handleRidePayment(PaymentRequest request) {
    processPassengerPayment(request);
    processDriverEarnings(request);
  }

  private void processPassengerPayment(PaymentRequest request) {
    Wallet passengerWallet = getWalletIfExists(request.passengerUsername());
    BigDecimal currentBalance = passengerWallet.getBalance();
    BigDecimal paymentAmount = request.price();

    if (currentBalance.compareTo(paymentAmount) < 0) {
      passengerWallet.setDebt(paymentAmount);
    } else {
      passengerWallet.setBalance(currentBalance.subtract(paymentAmount));
    }

    walletRepository.save(passengerWallet);
    transactionService.createTransaction(passengerWallet, paymentAmount.negate());
  }

  private void processDriverEarnings(PaymentRequest request) {
    Wallet driverWallet = getWalletIfExists(request.driverUsername());
    BigDecimal earnings = request.price().multiply(PaymentConstants.DRIVER_PAYOUT_RATE);

    driverWallet.setBalance(driverWallet.getBalance().add(earnings));

    walletRepository.save(driverWallet);
    transactionService.createTransaction(driverWallet, earnings);
  }

  private Wallet getWalletIfExists(String username) {
    return walletRepository
        .findByUsername(username)
        .orElseThrow(
            () -> new EntityNotFoundException(ExceptionKeys.WALLET_NOT_FOUND_USERNAME, username));
  }
}
