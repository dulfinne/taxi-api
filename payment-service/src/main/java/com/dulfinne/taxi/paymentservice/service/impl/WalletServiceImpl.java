package com.dulfinne.taxi.paymentservice.service.impl;

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
import com.dulfinne.taxi.paymentservice.service.TransactionService;
import com.dulfinne.taxi.paymentservice.service.WalletService;
import com.dulfinne.taxi.paymentservice.util.ExceptionKeys;
import com.dulfinne.taxi.paymentservice.util.PaymentConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
  private final WalletRepository repository;
  private final TransactionService transactionService;
  private final PaginatedMapper paginatedMapper;
  private final WalletMapper mapper;

  @Transactional(readOnly = true)
  @Override
  public PaginatedResponse<WalletResponse> getAllWallets(
      Integer offset, Integer limit, String sort, String order) {
    Sort.Direction direction = Sort.Direction.fromString(order);

    Page<Wallet> wallets =
        repository.findAll(PageRequest.of(offset, limit, Sort.by(direction, sort)));
    return paginatedMapper.toPaginatedResponse(wallets.map(mapper::toResponse));
  }

  @Transactional(readOnly = true)
  @Override
  public WalletResponse getWalletByUsername(String username) {
    Wallet wallet = getWalletIfExists(username);
    return mapper.toResponse(wallet);
  }

  @Transactional
  @Override
  public WalletResponse createWallet(String username) {
    checkUsernameUniqueness(username);

    Wallet wallet = new Wallet();
    wallet.setUsername(username);

    wallet = repository.save(wallet);
    return mapper.toResponse(wallet);
  }

  @Transactional
  @Override
  public WalletResponse creditMoney(String username, MoneyRequest request) {
    BigDecimal requestedAmount = request.amount();
    checkAmountLimits(request.amount());

    Wallet wallet = getWalletIfExists(username);
    BigDecimal currentBalance = wallet.getBalance();
    BigDecimal newBalance = currentBalance.add(requestedAmount);
    wallet.setBalance(newBalance);

    repository.save(wallet);
    transactionService.createTransaction(wallet, requestedAmount);
    return mapper.toResponse(wallet);
  }

  @Transactional
  @Override
  public WalletResponse debitMoney(String username, MoneyRequest request) {
    BigDecimal requestedAmount = request.amount();
    checkAmountLimits(requestedAmount);

    Wallet wallet = getWalletIfExists(username);
    BigDecimal currentBalance = wallet.getBalance();
    checkCanDebit(currentBalance, request.amount());

    BigDecimal newBalance = currentBalance.subtract(request.amount());
    wallet.setBalance(newBalance);

    repository.save(wallet);
    transactionService.createTransaction(wallet, requestedAmount.negate());
    return mapper.toResponse(wallet);
  }

  @Transactional
  @Override
  public WalletResponse repayDebt(String username) {
    Wallet wallet = getWalletIfExists(username);
    checkCanRepayDebt(wallet);

    BigDecimal currentBalance = wallet.getBalance();
    BigDecimal debt = wallet.getDebt();
    BigDecimal newBalance = currentBalance.subtract(debt);
    wallet.setBalance(newBalance);
    wallet.setDebt(BigDecimal.ZERO);

    repository.save(wallet);
    transactionService.createTransaction(wallet, debt.negate());
    return mapper.toResponse(wallet);
  }

  @Transactional(readOnly = true)
  @Override
  public CanPayByCardResponse canPayWithCard(String username) {
    Wallet wallet = getWalletIfExists(username);
    boolean canPayByCard = wallet.getDebt().compareTo(BigDecimal.ZERO) == 0;
    return new CanPayByCardResponse(canPayByCard);
  }

  private void checkCanRepayDebt(Wallet wallet) {
    BigDecimal debt = wallet.getDebt();
    if (debt.compareTo(BigDecimal.ZERO) == 0) {
      throw new ActionNotAllowedException(ExceptionKeys.DEBT_IS_ZERO);
    }

    if (wallet.getBalance().compareTo(debt) < 0) {
      throw new ActionNotAllowedException(ExceptionKeys.NOT_ENOUGH_MONEY_DEBT, debt);
    }
  }

  private void checkCanDebit(BigDecimal currentBalance, BigDecimal debitingAmount) {
    if (currentBalance.compareTo(debitingAmount) < 0) {
      throw new ActionNotAllowedException(ExceptionKeys.NOT_ENOUGH_MONEY_DEBIT, currentBalance);
    }
  }

  private void checkAmountLimits(BigDecimal amount) {
    if (!(amount.compareTo(PaymentConstants.MIN_AMOUNT_LIMIT) >= 0
        && amount.compareTo(PaymentConstants.MAX_AMOUNT_LIMIT) <= 0)) {
      throw new ActionNotAllowedException(
          ExceptionKeys.INVALID_MONEY_AMOUNT_LIMITS,
          PaymentConstants.MIN_AMOUNT_LIMIT,
          PaymentConstants.MAX_AMOUNT_LIMIT);
    }
  }

  private void checkUsernameUniqueness(String username) {
    if (repository.findByUsername(username).isPresent()) {
      throw new EntityAlreadyExistsException(ExceptionKeys.WALLET_EXISTS_USERNAME, username);
    }
  }

  private Wallet getWalletIfExists(String username) {
    return repository
        .findByUsername(username)
        .orElseThrow(
            () -> new EntityNotFoundException(ExceptionKeys.WALLET_NOT_FOUND_USERNAME, username));
  }
}
