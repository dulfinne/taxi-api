package com.dulfinne.taxi.paymentservice.service.impl;

import com.dulfinne.taxi.paymentservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.paymentservice.dto.response.TransactionResponse;
import com.dulfinne.taxi.paymentservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.paymentservice.mapper.PaginatedMapper;
import com.dulfinne.taxi.paymentservice.mapper.TransactionMapper;
import com.dulfinne.taxi.paymentservice.model.Transaction;
import com.dulfinne.taxi.paymentservice.model.Wallet;
import com.dulfinne.taxi.paymentservice.repository.TransactionRepository;
import com.dulfinne.taxi.paymentservice.repository.WalletRepository;
import com.dulfinne.taxi.paymentservice.service.TransactionService;
import com.dulfinne.taxi.paymentservice.util.ExceptionKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
  private final TransactionRepository repository;
  private final TransactionMapper mapper;
  private final PaginatedMapper paginatedMapper;
  private final WalletRepository walletRepository;

  @Transactional(readOnly = true)
  @Override
  public PaginatedResponse<TransactionResponse> getAllTransactions(
      Integer offset, Integer limit, String sort, String order) {
    Sort.Direction direction = Sort.Direction.fromString(order);

    Page<Transaction> transactions =
        repository.findAll(PageRequest.of(offset, limit, Sort.by(direction, sort)));
    return paginatedMapper.toPaginatedResponse(transactions.map(mapper::toResponse));
  }

  @Transactional(readOnly = true)
  @Override
  public PaginatedResponse<TransactionResponse> getTransactionsByUsername(
      String username, Integer offset, Integer limit, String sort, String order) {
    Sort.Direction direction = Sort.Direction.fromString(order);

    Wallet wallet = getWalletIfExists(username);
    Page<Transaction> transactions =
        repository.findByWalletId(
            wallet.getId(), PageRequest.of(offset, limit, Sort.by(direction, sort)));
    return paginatedMapper.toPaginatedResponse(transactions.map(mapper::toResponse));
  }

  @Transactional
  @Override
  public void createTransaction(Wallet wallet, BigDecimal amount, String description) {
    Transaction transaction = new Transaction();
    transaction.setWallet(wallet);
    transaction.setAmount(amount);
    transaction.setDescription(description);
    repository.save(transaction);
  }

  private Wallet getWalletIfExists(String username) {
    return walletRepository
        .findByUsername(username)
        .orElseThrow(
            () -> new EntityNotFoundException(ExceptionKeys.WALLET_NOT_FOUND_USERNAME, username));
  }
}
