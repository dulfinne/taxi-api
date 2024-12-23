package com.dulfinne.taxi.paymentservice.unit.service;

import com.dulfinne.taxi.paymentservice.dto.request.PaymentRequest;
import com.dulfinne.taxi.paymentservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.paymentservice.model.Wallet;
import com.dulfinne.taxi.paymentservice.repository.WalletRepository;
import com.dulfinne.taxi.paymentservice.service.TransactionService;
import com.dulfinne.taxi.paymentservice.service.impl.PaymentServiceImpl;
import com.dulfinne.taxi.paymentservice.util.PaymentConstants;
import com.dulfinne.taxi.paymentservice.util.PaymentTestData;
import com.dulfinne.taxi.paymentservice.util.WalletTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

  @InjectMocks private PaymentServiceImpl paymentService;

  @Mock private WalletRepository walletRepository;
  @Mock private TransactionService transactionService;

  @Test
  void handleRidePayment_whenValid_thenProcessPayments() {
    PaymentRequest request = PaymentTestData.getPaymentRequest();

    String passengerUsername = PaymentTestData.PASSENGER_USERNAME;
    String driverUsername = PaymentTestData.DRIVER_USERNAME;
    Wallet passengerWallet = WalletTestData.getWallet();
    Wallet driverWallet = WalletTestData.getCreatedWallet();

    BigDecimal expectedPassengerBalance = passengerWallet.getBalance().subtract(request.price());
    BigDecimal expectedDriverBalance =
        driverWallet
            .getBalance()
            .add(request.price())
            .multiply(PaymentConstants.DRIVER_PAYOUT_RATE);
    BigDecimal expectedDebt = passengerWallet.getDebt();

    // Arrange
    when(walletRepository.findByUsername(passengerUsername))
        .thenReturn(Optional.of(passengerWallet));
    when(walletRepository.findByUsername(driverUsername)).thenReturn(Optional.of(driverWallet));

    // Act
    paymentService.handleRidePayment(request);

    // Assert
    assertEquals(passengerWallet.getBalance(), expectedPassengerBalance);
    assertEquals(driverWallet.getBalance(), expectedDriverBalance);
    assertEquals(passengerWallet.getDebt(), expectedDebt);
    verify(walletRepository, times(2)).findByUsername(any(String.class));
    verify(walletRepository, times(2)).save(any(Wallet.class));
    verify(transactionService, times(2))
        .createTransaction(any(Wallet.class), any(BigDecimal.class), any(String.class));
  }

  @Test
  void handleRidePayment_whenPassengerBalanceIsZero_thenProcessPayments() {
    PaymentRequest request = PaymentTestData.getPaymentRequest();

    String passengerUsername = PaymentTestData.PASSENGER_USERNAME;
    String driverUsername = PaymentTestData.DRIVER_USERNAME;
    Wallet passengerWallet = WalletTestData.getCreatedWallet();
    Wallet driverWallet = WalletTestData.getCreatedWallet();

    BigDecimal expectedPassengerBalance = passengerWallet.getBalance();
    BigDecimal expectedDriverBalance =
        driverWallet
            .getBalance()
            .add(request.price())
            .multiply(PaymentConstants.DRIVER_PAYOUT_RATE);
    BigDecimal expectedDebt = request.price();

    // Arrange
    when(walletRepository.findByUsername(passengerUsername))
        .thenReturn(Optional.of(passengerWallet));
    when(walletRepository.findByUsername(driverUsername)).thenReturn(Optional.of(driverWallet));

    // Act
    paymentService.handleRidePayment(request);

    // Assert
    assertEquals(passengerWallet.getBalance(), expectedPassengerBalance);
    assertEquals(driverWallet.getBalance(), expectedDriverBalance);
    assertEquals(passengerWallet.getDebt(), expectedDebt);
    verify(walletRepository, times(2)).findByUsername(any(String.class));
    verify(walletRepository, times(2)).save(any(Wallet.class));
    verify(transactionService, times(2))
        .createTransaction(any(Wallet.class), any(BigDecimal.class), any(String.class));
  }

  @Test
  void handleRidePayment_whenWalletNotFound_thenThrowEntityNotFoundException() {
    PaymentRequest request = PaymentTestData.getPaymentRequest();

    // Arrange
    when(walletRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> paymentService.handleRidePayment(request));
  }
}
