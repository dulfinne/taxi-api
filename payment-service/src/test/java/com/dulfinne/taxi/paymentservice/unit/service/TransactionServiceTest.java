package com.dulfinne.taxi.paymentservice.unit.service;

import com.dulfinne.taxi.paymentservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.paymentservice.dto.response.TransactionResponse;
import com.dulfinne.taxi.paymentservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.paymentservice.mapper.PaginatedMapper;
import com.dulfinne.taxi.paymentservice.mapper.TransactionMapper;
import com.dulfinne.taxi.paymentservice.model.Transaction;
import com.dulfinne.taxi.paymentservice.model.Wallet;
import com.dulfinne.taxi.paymentservice.repository.TransactionRepository;
import com.dulfinne.taxi.paymentservice.repository.WalletRepository;
import com.dulfinne.taxi.paymentservice.service.impl.TransactionServiceImpl;
import com.dulfinne.taxi.paymentservice.util.DescriptionConstants;
import com.dulfinne.taxi.paymentservice.util.PaginationTestData;
import com.dulfinne.taxi.paymentservice.util.TransactionTestData;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

  @InjectMocks private TransactionServiceImpl service;

  @Mock private TransactionRepository transactionRepository;
  @Mock private WalletRepository walletRepository;
  @Spy private PaginatedMapper paginatedMapper = new PaginatedMapper();
  @Spy private TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);

  @Test
  void getAllTransactions_whenValid_thenReturnPaginatedTransactionResponse() {
    Transaction transaction = TransactionTestData.getTransaction();
    Page<Transaction> transactions = new PageImpl<>(List.of(transaction, transaction));
    TransactionResponse response = TransactionTestData.getTransactionResponse();
    PaginatedResponse<TransactionResponse> expected =
        PaginationTestData.getPaginatedResponse(List.of(response, response));

    // Arrange
    when(transactionRepository.findAll(any(Pageable.class))).thenReturn(transactions);

    // Act
    PaginatedResponse<TransactionResponse> result =
        service.getAllTransactions(
            PaginationTestData.DEFAULT_OFFSET,
            PaginationTestData.DEFAULT_LIMIT,
            PaginationTestData.SORT_FIELD,
            PaginationTestData.SORT_ORDER);

    // Assert
    assertEquals(expected, result);
    assertEquals(expected.totalElements(), result.totalElements());
    assertEquals(expected.content(), result.content());

    verify(transactionRepository, times(1)).findAll(any(Pageable.class));
    verify(paginatedMapper, times(1)).toPaginatedResponse(any(Page.class));
    verify(transactionMapper, times(expected.content().size())).toResponse(any(Transaction.class));
  }

  @Test
  void getTransactionsByUsername_whenValid_thenReturnPaginatedTransactionResponse() {
    String username = WalletTestData.WALLET_USERNAME;
    Wallet wallet = WalletTestData.getWallet();

    Transaction transaction = TransactionTestData.getTransaction();
    Page<Transaction> transactions = new PageImpl<>(List.of(transaction, transaction));
    TransactionResponse response = TransactionTestData.getTransactionResponse();
    PaginatedResponse<TransactionResponse> expected =
        PaginationTestData.getPaginatedResponse(List.of(response, response));

    // Arrange
    when(walletRepository.findByUsername(username)).thenReturn(Optional.of(wallet));
    when(transactionRepository.findByWalletId(any(Long.class), any(Pageable.class)))
        .thenReturn(transactions);

    // Act
    PaginatedResponse<TransactionResponse> result =
        service.getTransactionsByUsername(
            username,
            PaginationTestData.DEFAULT_OFFSET,
            PaginationTestData.DEFAULT_LIMIT,
            PaginationTestData.SORT_FIELD,
            PaginationTestData.SORT_ORDER);

    // Assert
    assertEquals(expected, result);
    assertEquals(expected.totalElements(), result.totalElements());
    assertEquals(expected.content(), result.content());

    verify(transactionRepository, times(1)).findByWalletId(any(Long.class), any(Pageable.class));
    verify(walletRepository, times(1)).findByUsername(username);
    verify(paginatedMapper, times(1)).toPaginatedResponse(any(Page.class));
    verify(transactionMapper, times(expected.content().size())).toResponse(any(Transaction.class));
  }

  @Test
  void getTransactionsByUsername_whenWalletNotFound_thenThrowEntityNotFoundException() {
    String username = WalletTestData.WALLET_USERNAME;

    // Arrange
    when(walletRepository.findByUsername(username)).thenReturn(Optional.empty());

    // Act
    assertThrows(
        EntityNotFoundException.class,
        () ->
            service.getTransactionsByUsername(
                username,
                PaginationTestData.DEFAULT_OFFSET,
                PaginationTestData.DEFAULT_LIMIT,
                PaginationTestData.SORT_FIELD,
                PaginationTestData.SORT_ORDER));
  }

  @Test
  public void createTransaction_whenValid_thenReturnTransactionResponse() {
    Wallet wallet = WalletTestData.getWallet();
    BigDecimal amount = TransactionTestData.TRANSACTION_AMOUNT;

    // Act
    service.createTransaction(wallet, amount, DescriptionConstants.WALLET_CREDIT);

    // Assert
    verify(transactionRepository, times(1)).save(any(Transaction.class));
  }
}
