package com.dulfinne.taxi.paymentservice.unit.service;

import com.dulfinne.taxi.paymentservice.dto.request.MoneyRequest;
import com.dulfinne.taxi.paymentservice.dto.response.CanPayByCardResponse;
import com.dulfinne.taxi.paymentservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.paymentservice.dto.response.WalletResponse;
import com.dulfinne.taxi.paymentservice.exception.ActionNotAllowedException;
import com.dulfinne.taxi.paymentservice.exception.EntityAlreadyExistsException;
import com.dulfinne.taxi.paymentservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.paymentservice.mapper.PaginatedMapper;
import com.dulfinne.taxi.paymentservice.mapper.WalletMapper;
import com.dulfinne.taxi.paymentservice.model.Wallet;
import com.dulfinne.taxi.paymentservice.repository.WalletRepository;

import com.dulfinne.taxi.paymentservice.service.impl.TransactionServiceImpl;
import com.dulfinne.taxi.paymentservice.service.impl.WalletServiceImpl;
import com.dulfinne.taxi.paymentservice.util.PaginationTestData;
import com.dulfinne.taxi.paymentservice.util.WalletTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

  @InjectMocks private WalletServiceImpl walletService;

  @Mock private WalletRepository repository;
  @Mock private TransactionServiceImpl transactionService;
  @Spy private PaginatedMapper paginatedMapper = new PaginatedMapper();
  @Spy private WalletMapper walletMapper = Mappers.getMapper(WalletMapper.class);

  @Test
  void getAllWallets_whenValid_thenReturnPaginatedWalletResponse() {
    Wallet wallet = WalletTestData.getWallet();
    Page<Wallet> wallets = new PageImpl(List.of(wallet, wallet));
    WalletResponse response = WalletTestData.getWalletResponse();
    PaginatedResponse<WalletResponse> expected =
        PaginationTestData.getPaginatedResponse(List.of(response, response));

    // Arrange
    when(repository.findAll(any(Pageable.class))).thenReturn(wallets);

    // Act
    PaginatedResponse<WalletResponse> result =
        walletService.getAllWallets(
            PaginationTestData.DEFAULT_OFFSET,
            PaginationTestData.DEFAULT_LIMIT,
            PaginationTestData.SORT_FIELD,
            PaginationTestData.SORT_ORDER);

    // Assert
    assertEquals(expected, result);
    assertEquals(expected.totalElements(), result.totalElements());
    assertEquals(expected.content(), result.content());

    verify(repository, times(1)).findAll(any(Pageable.class));
    verify(paginatedMapper, times(1)).toPaginatedResponse(any(Page.class));
    verify(walletMapper, times(expected.content().size())).toResponse(any(Wallet.class));
  }

  @Test
  void getWalletByUsername_whenValid_thenReturnWalletResponse() {
    String username = WalletTestData.WALLET_USERNAME;
    Wallet wallet = WalletTestData.getWallet();
    WalletResponse expected = WalletTestData.getWalletResponse();

    // Arrange
    when(repository.findByUsername(any(String.class))).thenReturn(Optional.of(wallet));

    // Act
    WalletResponse response = walletService.getWalletByUsername(username);

    // Assert
    assertEquals(expected, response);
    verify(repository, times(1)).findByUsername(username);
    verify(walletMapper, times(1)).toResponse(any(Wallet.class));
  }

  @Test
  void getWalletByUsername_whenWalletNotFound_thenThrowWalletNotFoundException() {
    String username = WalletTestData.WALLET_USERNAME;

    // Arrange
    when(repository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> walletService.getWalletByUsername(username));
  }

  @Test
  void createWallet_whenValid_thenReturnWalletResponse() {
    String username = WalletTestData.WALLET_USERNAME;
    Wallet wallet = WalletTestData.getCreatedWallet();
    WalletResponse expected = WalletTestData.getCreatedWalletResponse();

    // Arrange
    when(repository.findByUsername(any(String.class))).thenReturn(Optional.empty());
    when(repository.save(any(Wallet.class))).thenReturn(wallet);

    // Act
    WalletResponse result = walletService.createWallet(username);

    // Assert
    assertEquals(expected, result);
    verify(repository, times(1)).findByUsername(username);
    verify(repository, times(1)).save(any(Wallet.class));
    verify(walletMapper, times(1)).toResponse(any(Wallet.class));
  }

  @Test
  void createWallet_whenWalletAlreadyExist_thenThrowEntityAlreadyExistsException() {
    String username = WalletTestData.WALLET_USERNAME;
    Wallet wallet = WalletTestData.getCreatedWallet();

    // Arrange
    when(repository.findByUsername(any(String.class))).thenReturn(Optional.of(wallet));

    // Act & Assert
    assertThrows(EntityAlreadyExistsException.class, () -> walletService.createWallet(username));
  }

  @Test
  void creditMoney_whenValid_thenReturnWalletResponse() {
    String username = WalletTestData.WALLET_USERNAME;
    MoneyRequest request = new MoneyRequest(WalletTestData.CREDIT_MONEY_AMOUNT);
    Wallet wallet = WalletTestData.getWallet();
    WalletResponse expected = WalletTestData.getCreditedWalletResponse();

    // Arrange
    when(repository.findByUsername(any(String.class))).thenReturn(Optional.of(wallet));

    // Act
    WalletResponse result = walletService.creditMoney(username, request);

    // Assert
    assertEquals(expected, result);
    verify(repository, times(1)).findByUsername(username);
    verify(repository, times(1)).save(any(Wallet.class));
    verify(transactionService, times(1)).createTransaction(wallet, request.amount());
    verify(walletMapper, times(1)).toResponse(any(Wallet.class));
  }

  @Test
  void creditMoney_whenWalletNotFound_thenThrowEntityNotFoundException() {
    String username = WalletTestData.WALLET_USERNAME;
    MoneyRequest request = new MoneyRequest(WalletTestData.CREDIT_MONEY_AMOUNT);

    // Arrange
    when(repository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> walletService.creditMoney(username, request));
  }

  @Test
  void debitMoney_whenValid_thenReturnWalletResponse() {
    String username = WalletTestData.WALLET_USERNAME;
    MoneyRequest request = new MoneyRequest(WalletTestData.NORMAL_DEBIT_MONEY_AMOUNT);
    Wallet wallet = WalletTestData.getWallet();
    WalletResponse expected = WalletTestData.getDebitedWalletResponse();

    // Arrange
    when(repository.findByUsername(any(String.class))).thenReturn(Optional.of(wallet));

    // Act
    WalletResponse result = walletService.debitMoney(username, request);

    // Assert
    assertEquals(expected, result);
    verify(repository, times(1)).findByUsername(username);
    verify(repository, times(1)).save(any(Wallet.class));
    verify(transactionService, times(1)).createTransaction(wallet, request.amount().negate());
    verify(walletMapper, times(1)).toResponse(any(Wallet.class));
  }

  @Test
  void debitMoney_whenDebitAmountExceedBalance_thenThrowActionNotAllowedException() {
    String username = WalletTestData.WALLET_USERNAME;
    MoneyRequest request = new MoneyRequest(WalletTestData.TOO_BIG_DEBIT_MONEY_AMOUNT);
    Wallet wallet = WalletTestData.getWallet();

    // Arrange
    when(repository.findByUsername(any(String.class))).thenReturn(Optional.of(wallet));

    // Act & Assert
    assertThrows(
        ActionNotAllowedException.class, () -> walletService.debitMoney(username, request));
  }

  @Test
  void debitMoney_whenWalletNotFound_thenThrowEntityNotFoundException() {
    String username = WalletTestData.WALLET_USERNAME;
    MoneyRequest request = new MoneyRequest(WalletTestData.TOO_BIG_DEBIT_MONEY_AMOUNT);

    // Arrange
    when(repository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> walletService.debitMoney(username, request));
  }

  @Test
  void repayDebt_whenValid_thenReturnWalletResponse() {
    BigDecimal debt = WalletTestData.WALLET_DEBT;
    String username = WalletTestData.WALLET_USERNAME;
    Wallet wallet = WalletTestData.getWallet();
    WalletResponse expected = WalletTestData.getRepayedWalletResponse();

    // Arrange
    when(repository.findByUsername(any(String.class))).thenReturn(Optional.of(wallet));

    // Act
    WalletResponse result = walletService.repayDebt(username);

    // Assert
    assertEquals(expected, result);
    verify(repository, times(1)).findByUsername(username);
    verify(repository, times(1)).save(any(Wallet.class));
    verify(transactionService, times(1)).createTransaction(wallet, debt.negate());
    verify(walletMapper, times(1)).toResponse(any(Wallet.class));
  }

  @Test
  void repayDebt_whenDebtExceedBalance_thenThrowActionNotAllowedException() {
    BigDecimal debt = WalletTestData.WALLET_DEBT;
    String username = WalletTestData.WALLET_USERNAME;
    Wallet wallet = WalletTestData.getWallet();
    wallet.setBalance(debt.subtract(BigDecimal.ONE));

    // Arrange
    when(repository.findByUsername(any(String.class))).thenReturn(Optional.of(wallet));

    // Act & Assert
    assertThrows(ActionNotAllowedException.class, () -> walletService.repayDebt(username));
  }

  @Test
  void repayDebt_whenDebtIsZero_thenThrowActionNotAllowedException() {
    String username = WalletTestData.WALLET_USERNAME;
    Wallet wallet = WalletTestData.getCreatedWallet();

    // Arrange
    when(repository.findByUsername(any(String.class))).thenReturn(Optional.of(wallet));

    // Act & Assert
    assertThrows(ActionNotAllowedException.class, () -> walletService.repayDebt(username));
  }

  @Test
  void repayDebt_whenWalletNotFound_thenThrowEntityNotFoundException() {
    String username = WalletTestData.WALLET_USERNAME;

    // Arrange
    when(repository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> walletService.repayDebt(username));
  }

  @Test
  void canPayWithCard_whenDebtIsZero_thenReturnTrue() {
    String username = WalletTestData.WALLET_USERNAME;
    Wallet wallet = WalletTestData.getCreatedWallet();

    // Arrange
    when(repository.findByUsername(any(String.class))).thenReturn(Optional.of(wallet));

    // Act
    CanPayByCardResponse result = walletService.canPayWithCard(username);

    // Assert
    assertTrue(result.canPayByCard());
    verify(repository, times(1)).findByUsername(username);
  }

  @Test
  void canPayWithCard_whenDebtExists_thenReturnTrue() {
    String username = WalletTestData.WALLET_USERNAME;
    Wallet wallet = WalletTestData.getWallet();

    // Arrange
    when(repository.findByUsername(any(String.class))).thenReturn(Optional.of(wallet));

    // Act
    CanPayByCardResponse result = walletService.canPayWithCard(username);

    // Assert
    assertFalse(result.canPayByCard());
    verify(repository, times(1)).findByUsername(username);
  }

  @Test
  void canPayWithCard_whenWalletNotFound_thenThrowEntityNotFoundException() {
    String username = WalletTestData.WALLET_USERNAME;

    // Arrange
    when(repository.findByUsername(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> walletService.canPayWithCard(username));
  }
}
